package com.rabbi.services.impl;

import com.rabbi.configuration.JwtProvider;
import com.rabbi.domain.UserRole;
import com.rabbi.exception.UserException;
import com.rabbi.mapper.UserMapper;
import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.payload.response.AuthResponse;
import com.rabbi.repo.UserRespository;
import com.rabbi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(String username, String password) {
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        String token = jwtProvider.generateToken(authentication);

        User user = userRespository.findByEmail(username);
        user.setLastLogin(LocalDateTime.now());
        userRespository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login Successful");
        response.setMessage("Welcome back " + user.getFullName());
        response.setJwt(token);
        response.setUser(UserMapper.toDTO(user));
        return response;
    }


    private Authentication authenticate(String username, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
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

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Welcome " + savedUser.getFullName());
        response.setMessage("User registered successfully");
        response.setUser(UserMapper.toDTO(savedUser));
        return response;
    }

    @Override
    public void createPasswordResetToken(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
