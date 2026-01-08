package com.rabbi.controller;

import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile() throws Exception {
        return ResponseEntity.ok(
                userService.getCurrentUser()
        );
    }

}
