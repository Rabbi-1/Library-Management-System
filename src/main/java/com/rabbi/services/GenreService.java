package com.rabbi.services;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);

}
