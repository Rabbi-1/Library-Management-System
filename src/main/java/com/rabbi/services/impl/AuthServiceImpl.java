package com.rabbi.services.impl;

import com.rabbi.domain.UserRole;
import com.rabbi.exception.UserException;
import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.payload.response.AuthResponse;
import com.rabbi.repo.UserRespository;
import com.rabbi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(String email, String password) {
        return null;
    }

    @Override
    public AuthResponse signup(UserDTO req) throws UserException {
        User user = userRespository.findByEmail(req.getEmail());
        if (user == null) {
            throw new UserException("email id already registered");
        }

        User createUser = new User();
        createUser.setEmail(req.getEmail());
        createUser.setPassword(passwordEncoder.encode(req.getPassword())); // Encrypt the password
        createUser.setPhone(req.getPhone());
        createUser.setFullName(req.getFullName());
        createUser.setLastLogin(LocalDateTime.now());
        createUser.setRole(UserRole.RoLE_USER);

        User savedUser = userRespository.save(createUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void createPasswordResetToken(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
