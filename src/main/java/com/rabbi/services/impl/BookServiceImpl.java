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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public BookDTO getBooksByISBN(String isbn) {
        return null;
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBook(Long bookId) {

    }

    @Override
    public void hardDeleteBook(Long bookId) {

    }

    @Override
    public PagesResponse<BookDTO> searchBooksWithFilters(BookSearchRequest criteria) {
        return null;
    }

    @Override
    public long getTotalActiveBooks() {
        return 0;
    }

    @Override
    public long getTotalAvailableBooks() {
        return 0;
    }
}
