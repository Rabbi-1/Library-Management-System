package com.rabbi.repo;

import com.rabbi.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;



public interface GenreRepository extends JpaRepository<Genre, Long> {

}
