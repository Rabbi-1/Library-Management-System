package com.rabbi.mapper;

import com.rabbi.exception.BookException;
import com.rabbi.model.Book;
import com.rabbi.model.Genre;
import com.rabbi.payload.dto.BookDTO;
import com.rabbi.repo.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final GenreRepository genreRepository;

    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        BookDTO dto = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .genreId(book.getGenre().getId())
                .genreName(book.getGenre().getName())
                .genreCode(book.getGenre().getCode())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .language(book.getLanguage())
                .pages(book.getPages())
                .description(book.getDescription())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .price(book.getPrice() != null ? book.getPrice().doubleValue() : null)
                .coverImageUrl(book.getCoverImageUrl())
                .active(book.getActive())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
        return dto;
    }

    public Book toEntity(BookDTO dto) throws BookException {
        if (dto == null) {
            return null;
        }
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(dto.getAuthor());

        // Note: Genre should be set separately in the service layer
        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> new BookException("Genre not found with id: " + dto.getGenreId()));
            book.setGenre(genre);
        }

        book.setPublisher(dto.getPublisher());
        book.setPublicationDate(dto.getPublicationDate());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        if (dto.getPrice() != null) {
            book.setPrice(java.math.BigDecimal.valueOf(dto.getPrice()));
        }
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setActive(true);


        return book;

    }

    public void updateEntityFromDTO(BookDTO dto, Book book) throws BookException {
        if (dto == null || book == null) {
            return;
        }
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> new BookException("Genre not found with id: " + dto.getGenreId()));
            book.setGenre(genre);
    }
        book.setPublisher(dto.getPublisher());
        book.setPublicationDate(dto.getPublicationDate());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setPrice(dto.getPrice() != null ? java.math.BigDecimal.valueOf(dto.getPrice()) : null);
        book.setCoverImageUrl(dto.getCoverImageUrl());

        if(dto.getActive() != null) {
            book.setActive(dto.getActive());
        }
    }
}

/**
 BookMapper
----------
    Purpose:
 - Converts between Book (database entity) and BookDTO (API data)

    Methods:
 - toDTO(Book)
  → Entity → DTO
  → Used when returning book data to the client

- toEntity(BookDTO)
  → DTO → Entity
  → Used when creating a new book
  → Fetches Genre by genreId (throws error if not found)

- updateEntityFromDTO(BookDTO, Book)
  → Updates an existing Book
  → Overwrites fields from DTO
  → Updates Genre only if genreId is provided

 Key Rule:
- Genre is always validated via GenreRepository
- DTO = API layer
- Entity = Database layer

*/