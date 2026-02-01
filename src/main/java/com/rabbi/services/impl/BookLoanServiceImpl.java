package com.rabbi.services.impl;

import com.rabbi.domain.BookLoanStatus;
import com.rabbi.domain.BookLoanType;
import com.rabbi.exception.BookException;
import com.rabbi.mapper.BookLoanMapper;
import com.rabbi.model.Book;
import com.rabbi.model.BookLoan;
import com.rabbi.model.User;
import com.rabbi.payload.dto.BookLoanDTO;
import com.rabbi.payload.dto.SubscriptionDTO;
import com.rabbi.payload.request.BookLoanSearchRequest;
import com.rabbi.payload.request.CheckinRequest;
import com.rabbi.payload.request.CheckoutRequest;
import com.rabbi.payload.request.RenewalRequest;
import com.rabbi.payload.response.PagesResponse;
import com.rabbi.repo.BookLoanRepository;
import com.rabbi.repo.BookRepository;
import com.rabbi.services.BookLoanService;
import com.rabbi.services.SubscriptionService;
import com.rabbi.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final BookRepository bookRepository;
    private final BookLoanMapper bookLoanMapper;

    @Override
    public BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws Exception {
        User user = userService.getCurrentUser();
        return checkoutBookForUser(user.getId(), checkoutRequest);
    }

    @Override
    public BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception {
        User user = userService.findById(userId);
        //check if user has active subscription or not
        SubscriptionDTO subscription = subscriptionService.getUsersActiveSubscriptions(user.getId());
        Book book = bookRepository.findById(checkoutRequest.getBookId())
                .orElseThrow(() -> new BookException("book not found with this id"));
        if(!book.getActive()) {
            throw new BookException("book is not active");
        }
        if(book.getAvailableCopies() <= 0) {
            throw new BookException("book has no available copies");
        }

        //check if a user has already checked out this book
        if(bookLoanRepository.hasActiveCheckout(userId, book.getId())) {
            throw new BookException("book has already checked out");
        }

        // how many checkouts left?
        long activeCheckouts = bookLoanRepository.countActiveBookLoansByUser(userId);
        int maxBooksAllowed = subscription.getMaxBooksAllowed();
        if(activeCheckouts >= maxBooksAllowed) {
            throw new BookException("You have reached your maximum number of book loans");
        }

        //check for any overdue books
        long overdueCount = bookLoanRepository.countOverdueBookLoansByUser(userId);
        if(overdueCount > 0) {
            throw new Exception("First return the old overdue book");
        }
        //create a book loan
        BookLoan bookLoan = BookLoan.builder()
                .user(user)
                .book(book)
                .type(BookLoanType.CHECKOUT)
                .status(BookLoanStatus.CHECKED_OUT)
                .checkoutDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(checkoutRequest.getCheckoutDays()))
                .renewalCount(0)
                .maxRenewals(2)
                .notes(checkoutRequest.getNotes())
                .isOverDue(false)
                .overdueDays(0)
                .build();
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toDTO(savedBookLoan);
    }

    @Override
    public BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception {
        //Validate Book loan exists
        BookLoan bookLoan = bookLoanRepository.findById(checkinRequest.getBookLoanId())
                .orElseThrow( () -> new Exception("Book loan not found"));
        //Check if already returned
        if(!bookLoan.isActive()) {
            throw new BookException("book is not active");
        }
        //Set return dates
        bookLoan.setReturnDate(LocalDate.now());


        BookLoanStatus condition = checkinRequest.getCondition();
        if(condition == null) {
            condition = BookLoanStatus.RETURNED;
        }
        bookLoan.setOverdueDays(0);
        bookLoan.setIsOverDue(false);
        bookLoan.setNotes("book loan return by user");
        bookLoan.setStatus(condition);
        //Update book availability
        if(condition != BookLoanStatus.LOST) {
            Book book = bookLoan.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }

        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toDTO(savedBookLoan);
    }

    @Override
    public BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception {
        BookLoan bookLoan = bookLoanRepository.findById(renewalRequest.getBookLoanId())
                .orElseThrow( () -> new Exception("Book loan not found"));
        //check if you can be renewed
        if(!bookLoan.canRenew()){
            throw new BookException("Book loan can't renew");
        }
        //update due date
        bookLoan.setDueDate(bookLoan.getDueDate().plusDays(renewalRequest.getExtensionDays()));
        bookLoan.setRenewalCount(bookLoan.getRenewalCount() + 1);
        bookLoan.setNotes("book renewed by user");
        BookLoan saved = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toDTO(saved);
    }

    @Override
    public PagesResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) throws Exception {
        User currentUser = userService.getCurrentUser();
        Page<BookLoan> bookLoanPage;

        if(status != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("dueDate").ascending());
            bookLoanPage = bookLoanRepository.findByStatusAndUser(status, currentUser, pageable);

        } else{
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            bookLoanPage = bookLoanRepository.findByUserId(currentUser.getId(), pageable);
        }
            return convertToPageResponse(bookLoanPage);
    }
    //TODO: Review this
    @Override
    public PagesResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest searchRequest) throws Exception {
        Pageable pageable = createPageable(
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
        Page<BookLoan> bookLoanPage;
        if(Boolean.TRUE.equals(searchRequest.getOverdueOnly())) {
            bookLoanPage = bookLoanRepository.findOverdueBookLoans(LocalDate.now(),pageable);
        }
        else if(searchRequest.getUserId() != null) {
            bookLoanPage = bookLoanRepository.findByUserId(searchRequest.getUserId(), pageable);
        }
        else if (searchRequest.getBookId() != null) {
            bookLoanPage = bookLoanRepository.findByBookId(searchRequest.getBookId(), pageable);
        }
        else if (searchRequest.getStatus() != null) {
            // Fetch loans by loan status
            bookLoanPage = bookLoanRepository.findByStatus(searchRequest.getStatus(), pageable);
        }
        else if (searchRequest.getStartDate() != null && searchRequest.getEndDate() != null) {
            // Fetch loans within date range
            bookLoanPage = bookLoanRepository
                    .findBookLoansByDateRange(
                            searchRequest.getStartDate(),
                            searchRequest.getEndDate(),
                            pageable
                    );
        } else {
            bookLoanPage = bookLoanRepository.findAll(pageable);
        }

        return convertToPageResponse(bookLoanPage);
    }

    @Override
    public int updateOverdueBookLoan() {
        return 0;
    }




    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        size = Math.min(size, 100);
        size = Math.max(size, 1);
        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    private PagesResponse<BookLoanDTO> convertToPageResponse(Page<BookLoan> bookLoanPage) {
        List<BookLoanDTO> bookLoanDTOs = bookLoanPage
                .getContent()
                .stream()
                .map(bookLoanMapper::toDTO)
                .collect(Collectors.toList());
        return new PagesResponse<>(
                bookLoanDTOs,
                bookLoanPage.getNumber(),
                bookLoanPage.getSize(),
                bookLoanPage.getTotalElements(),
                bookLoanPage.getTotalPages(),
                bookLoanPage.isLast(),
                bookLoanPage.isFirst(),
                bookLoanPage.isEmpty()
        );
    }
}
