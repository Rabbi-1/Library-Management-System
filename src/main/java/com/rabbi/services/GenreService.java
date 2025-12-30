package com.rabbi.services;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long genreId);

    GenreDTO updateGenre(Long genreId, GenreDTO genreDTO);

    void deleteGenre(Long genreId);

    void hardDeleteGenre(Long genreId);

    List<GenreDTO> getActiveGenresWithSubGenres();

    List<GenreDTO> getTopLevelGenres();

    //Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);

    long getTotalActiveGenres();

    long getBookCountByGenreId(Long genreId);



}
