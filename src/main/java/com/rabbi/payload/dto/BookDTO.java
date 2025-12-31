package com.rabbi.payload.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters")
    private String author;

    @NotNull(message = "Genre ID is required")
    private Long genreId;

    private String genreName;
    private String genreCode;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    private LocalDate publicationDate;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Min(value = 1, message = "Pages must be at least 1")
    @Max(value = 50000, message = "Pages must not exceed 50000")
    private Integer pages;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Min(value = 0, message = "Available copies cannot be negative")
    @NotNull(message = "Total copies is required")
    private Integer totalCopies;

    @Min(value = 0, message = "Available copies cannot be negative")
    @NotNull(message = "Available copies is required")
    private Integer availableCopies;

    @NotNull(message = "Price is required")
    @Digits(integer = 8, fraction = 2, message = "Price must be a valid monetary amount")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private Double price;

    @Size(max = 500, message = "Cover image URL must not exceed 500 characters")
    private String coverImageUrl;


    private Boolean alreadyHaveLoan;
    private Boolean alreadyHaveReservation;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
