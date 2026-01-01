package com.rabbi.mapper;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;
import com.rabbi.repo.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Component
public class GenreMapper {


    private final GenreRepository genreRepository;

    public GenreDTO toDTO(Genre savedGenre) {
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
                    .filter(Genre::getActive) // only include active sub-genres
                    .map(this::toDTO).collect(Collectors.toList()));

        }

//        dto.setBookCount(long(savedGenre.get));

        return dto;
    }

    public Genre toEntity(GenreDTO genreDTO) {
        if (genreDTO == null) {
            return null;
        }
        Genre genre = Genre.builder()
                .code(genreDTO.getCode())
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .displayOrder(genreDTO.getDisplayOrder())
                .active(true)
                .build();

        if(genreDTO.getParentGenreId() != null) {
            genreRepository.findById(genreDTO.getParentGenreId())
                    .ifPresent(genre::setParentGenre);
        }

        return genre;
    }

    public void updateEntityFromDTO(GenreDTO dto, Genre existingGenre) {
        if (dto == null || existingGenre == null) {
            return;
        }

        existingGenre.setCode(dto.getCode());
        existingGenre.setName(dto.getName());
        existingGenre.setDescription(dto.getDescription());
        existingGenre.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0); // default to 0 if null
        if(dto.getActive() != null) {
            existingGenre.setActive(dto.getActive());
        }

        if (dto.getParentGenreId() != null) {
            genreRepository.findById(dto.getParentGenreId())
                    .ifPresent(existingGenre::setParentGenre);
        }



    }

    public List<GenreDTO> toDTOList(List<Genre> genreList){
        return genreList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}


//GenreMapper is responsible for converting
// between Genre entities and GenreDTO objects.
//It acts as a bridge between the database model
// and the API layer, ensuring that genre data is
// formatted correctly for clients and safely
// transformed back into entities when saving or updating data.
//This keeps the domain model clean, prevents
// direct exposure of entities, and centralizes
// all genre-related mapping logic in one place.