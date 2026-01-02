package com.rabbi.services;

import com.rabbi.exception.BookException;
import com.rabbi.payload.dto.BookDTO;
import com.rabbi.payload.request.BookSearchRequest;
import com.rabbi.payload.response.PagesResponse;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException;
    BookDTO getBookById(Long bookId) throws BookException;
    BookDTO getBooksByISBN(String isbn) throws BookException;
    BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException;
    void deleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;

    PagesResponse<BookDTO> searchBooksWithFilters(BookSearchRequest criteria);

    long getTotalActiveBooks();

    long getTotalAvailableBooks();


}
