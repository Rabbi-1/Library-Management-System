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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public BookLoanDTO renewCheckout(RenewalRequest renewalRequest) {
        return null;
    }

    @Override
    public PagesResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) {
        return null;
    }

    @Override
    public PagesResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request) {
        return null;
    }

    @Override
    public int updateOverdueBookLoan() {
        return 0;
    }
}
