package com.rabbi.services;

import com.rabbi.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password);
}
