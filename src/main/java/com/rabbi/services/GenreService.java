package com.rabbi.services;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);

    List<GenreDTO> getAllGenres();

}
