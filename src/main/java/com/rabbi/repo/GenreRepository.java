package com.rabbi.repo;

import com.rabbi.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    // Custom query to find active genres ordered by display order
    List<Genre>findByActiveTrueOrderByDisplayOrderAsc();
    // Custom query to find top-level active genres (no parent genre) ordered by display order
    List<Genre>findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
    // Custom query to find active sub-genres of a given parent genre ordered by display order
    List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long parentGenreId);

    long countByActiveTrue();

//    @Query("select count(b) from book b where b.genre.id = :genreId")
//    long countBookByGenre(@Param("genreId") Long genreId);
}
