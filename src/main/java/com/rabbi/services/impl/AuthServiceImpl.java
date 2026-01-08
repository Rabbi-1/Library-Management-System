package com.rabbi.services.impl;

import com.rabbi.configuration.JwtProvider;
import com.rabbi.domain.UserRole;
import com.rabbi.exception.UserException;
import com.rabbi.mapper.UserMapper;
import com.rabbi.model.PasswordResetToken;
import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.payload.response.AuthResponse;
import com.rabbi.repo.PasswordResetTokenRepository;
import com.rabbi.repo.UserRespository;
import com.rabbi.services.AuthService;
import com.rabbi.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final CustomUserServiceImplementation customUserServiceImplementation;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Override
    public AuthResponse login(String username, String password) throws UserException {
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role = authorities.iterator().next().getAuthority();
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


    private Authentication authenticate(String username, String password) throws UserException {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(username,
                null,
                userDetails.getAuthorities());
    }

    @Override
    public AuthResponse signup(UserDTO req) throws UserException {
        User user = userRespository.findByEmail(req.getEmail());
        if (user != null) {
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

        // Manually authenticate the user by creating an Authentication object
        // and storing it in the SecurityContext so Spring Security treats
        // the user as logged in for the current request
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(),
                        savedUser.getPassword()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Welcome " + savedUser.getFullName());
        response.setMessage("User registered successfully");
        response.setUser(UserMapper.toDTO(savedUser));
        return response;
    }

    @Transactional
    public void createPasswordResetToken(String email) throws UserException {
        String frontendUrl = "http://localhost:5173";

        User user = userRespository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();
        passwordResetTokenRepository.save(resetToken);
        String resetLink = frontendUrl + token;
        String subject = "Password Reset Request";
        String body = "To reset your password, click the following link: " + resetLink;

        emailService.sendEmail(user.getEmail(), subject, body);

        //send email


    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws Exception {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(()-> new Exception("Invalid password reset token"));

        if(resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new Exception("Password reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRespository.save(user);
        passwordResetTokenRepository.delete(resetToken);

    }
}
