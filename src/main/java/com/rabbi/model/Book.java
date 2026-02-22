package com.rabbi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    //tells Spring to automatically create a
    //unique ID value for you when you save an entity to the database.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String author;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Genre genre;

    private String publisher;

    private LocalDate publicationDate;

    private  String language;

    private Integer pages;

    private String description;

    @Column(nullable = false)
    private Integer totalCopies;

    @Column(nullable = false)
    private Integer availableCopies;

    private BigDecimal price;

    private String coverImageUrl;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @AssertTrue(message = "Available copies cannot exceed total copies")
    public boolean isAvailableCopiesValid() {
        return availableCopies != null
                && totalCopies != null
                && availableCopies <= totalCopies
                && availableCopies >= 0;
    }
}
    /**
    Book represents a single physical or logical book item in the system.
    It serves as the core domain entity that stores identifying, descriptive,
    and inventory-related information about a book, including its title, author, ISBN, and publication details.
    Each book is associated with exactly one genre, enabling structured classification and filtering across the application.
    The class also tracks availability and inventory state (total vs. available copies),
    pricing, activation status, and audit timestamps, making
    it suitable for both catalog display and inventory management workflows.
     */