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

    //TODO: updateEntityFromDTO method can be added here if needed in future
}
//BookMapper converts Book entities into BookDTO objects
// for use in API responses. It prepares book data in a
// client-friendly format by flattening related information,
// such as genre details, and ensures the API does not expose
// the database entity directly. This class keeps mapping logic
// in one place and helps maintain a clean separation between
// the persistence layer and the API layer.