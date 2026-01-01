package com.rabbi.payload.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {

    private Long id;

    @NotBlank(message = "Genre code is mandatory")
    private String code;

    @NotBlank(message = "Genre name is mandatory")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 0, message = "Display order must be a non-negative integer")
    private Integer displayOrder = 0;

    private Boolean active;

    private Long parentGenreId;

    private String parentGenreName;

    private List<GenreDTO> subGenres;

    private Long bookCount;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

}

//GenreDTO is used to send and receive genre data through the API.
// It defines what information about a genre is shared with clients,
// including parent and sub-genre relationships, without exposing the
// database entity directly. This class keeps API communication clean
// and safe while supporting category navigation, display, and management
// features.
