package com.rabbi.services;

import com.rabbi.exception.GenreException;
import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long genreId) throws GenreException;

    GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException;

    void deleteGenre(Long genreId) throws GenreException;

    void hardDeleteGenre(Long genreId) throws GenreException;

    List<GenreDTO> getAllActiveGenresWithSubGenres();

    List<GenreDTO> getTopLevelGenres();

    //Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);

    long getTotalActiveGenres();

    long getBookCountByGenreId(Long genreId);



}
