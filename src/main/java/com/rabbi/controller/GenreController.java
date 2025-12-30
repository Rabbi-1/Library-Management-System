package com.rabbi.controller;

import com.rabbi.model.Genre;
import com.rabbi.payload.dto.GenreDTO;
import com.rabbi.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre) {
        GenreDTO createdGenre = genreService.createGenre(genre);
        return ResponseEntity.ok(createdGenre);

    }
}
