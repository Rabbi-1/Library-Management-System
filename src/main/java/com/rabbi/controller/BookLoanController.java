package com.rabbi.controller;

import com.rabbi.services.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-loans")
public class BookLoanController {
    private final BookLoanService bookLoanService;
}
