package com.rabbi.repo;

import com.rabbi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //SELECT * FROM book WHERE isbn = ?
    Optional<Book> findByIsbn(String isbn);
    // SELECT COUNT(*) > 0 FROM book WHERE isbn = ?
    boolean existsByIsbn(String isbn);

    @Query("""
    SELECT b FROM Book b
    WHERE (:searchTerm IS NULL OR
           LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
           LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
           LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    AND (:genreId IS NULL OR b.genre.id = :genreId)
    AND (:available = false OR b.availableCopies > 0)
    AND b.active = true
""")

    Page<Book> searchBookWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("available") Boolean available,
            Pageable pageable
    );

    long countByActiveTrue();
}
