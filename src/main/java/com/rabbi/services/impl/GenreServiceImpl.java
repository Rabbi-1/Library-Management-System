package com.rabbi.services.impl;

import com.rabbi.model.Genre;
import com.rabbi.repo.GenreRepository;
import com.rabbi.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
