package com.rabbi.services;

import com.rabbi.exception.UserException;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password);
    AuthResponse signup(UserDTO req) throws UserException;
    void createPasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
}
