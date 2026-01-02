package com.rabbi.services.impl;

import com.rabbi.exception.BookException;
import com.rabbi.mapper.BookMapper;
import com.rabbi.model.Book;
import com.rabbi.payload.dto.BookDTO;
import com.rabbi.payload.request.BookSearchRequest;
import com.rabbi.payload.response.PagesResponse;
import com.rabbi.repo.BookRepository;
import com.rabbi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDTO createBook(BookDTO bookDTO) throws BookException {
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new BookException("Book with ISBN already exists: " + bookDTO.getIsbn());
        }

        Book book = bookMapper.toEntity(bookDTO);

        book.isAvailableCopiesValid();
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException {

        List<BookDTO> createdBooks = new ArrayList<>();
        for(BookDTO bookDTO:bookDTOs) {
            BookDTO book = createBook(bookDTO);
            createdBooks.add(book);
        }
        return createdBooks;
    }

    @Override
    public BookDTO getBookById(Long bookId) throws BookException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found with id: " + bookId));
        return bookMapper.toDTO(book);

    }

    @Override
    public BookDTO getBooksByISBN(String isbn) throws BookException {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookException("Book not found with ISBN: " + isbn));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );
        bookMapper.updateEntityFromDTO(bookDTO, existingBook);
        existingBook.isAvailableCopiesValid();
        Book savedBook = bookRepository.save(existingBook);
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) throws BookException {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );
        existingBook.setActive(Boolean.FALSE);
        bookRepository.save(existingBook);

    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );
        bookRepository.delete(existingBook);
    }

    @Override
    public PagesResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
        Pageable pageable = createPageable(
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
        Page<Book> bookPage = bookRepository.searchBookWithFilters(
                searchRequest.getSearchTerm(),
                searchRequest.getGenreId(),
                searchRequest.getAvailableOnly(),
                pageable
        );
        return convertToPagesResponse(bookPage);
    }

    @Override
    public long getTotalActiveBooks() {
        return bookRepository.countByActiveTrue();
    }

    @Override
    public long getTotalAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        size = Math.min(size, 10);
        size = Math.max(size, 1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    private PagesResponse<BookDTO> convertToPagesResponse(Page<Book> books) {
        List<BookDTO> bookDTOS = books.getContent()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
        return new PagesResponse<>(
                bookDTOS,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isLast(),
                books.isFirst(),
                books.isEmpty()
        );
    }
}
