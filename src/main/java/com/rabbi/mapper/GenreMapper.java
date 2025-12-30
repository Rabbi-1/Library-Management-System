package com.rabbi.mapper;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;

import java.util.stream.Collectors;

public class GenreMapper {

    public static GenreDTO toDTO(Genre savedGenre) {
        if (savedGenre == null) {
            return null;
        }

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
        if(savedGenre.getSubGenres() != null && !savedGenre.getSubGenres().isEmpty()) { // only do this if there are sub genres
            dto.setSubGenres(savedGenre.getSubGenres().stream()
                    .filter(Genre::getActive) // only include active sub genres
                    .map(GenreMapper::toDTO).collect(Collectors.toList()));

        }

//        dto.setBookCount(long(savedGenre.get));

        return dto;
    }
}
