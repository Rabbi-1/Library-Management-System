package com.rabbi.services.impl;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;
import com.rabbi.repo.GenreRepository;
import com.rabbi.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
       // return genreRepository.save(genreDTO);
        Genre genre = Genre.builder()
                .code(genreDTO.getCode())
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .displayOrder(genreDTO.getDisplayOrder())
                .active(true)
                .build();


        if(genreDTO.getParentGenreId() != null) {
            Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId()).get();
            genre.setParentGenre(parentGenre);
        }


        Genre savedGenre = genreRepository.save(genre);

        GenreDTO dto = GenreDTO.builder()
                .id(savedGenre.getId())
                .code(savedGenre.getCode())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.getActive())
                .createAt(savedGenre.getCreateAt())
                .updatedAt(savedGenre.getUpdatedAt())
                .build();
        if(savedGenre.getParentGenre() != null) {
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }

//        dto.setSubGenres(savedGenre.getSubGenres().stream()
//                .filter(subGenre-> subGenre.getActive())
//                .map(subGenre ->));
//        dto.setBookCount(long(savedGenre.get));
        return dto;
    }
}

















