package com.rabbi.controller;


import com.rabbi.exception.UserException;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.payload.request.ForgotPasswordRequest;
import com.rabbi.payload.request.LoginRequest;
import com.rabbi.payload.request.ResetPasswordRequest;
import com.rabbi.payload.response.ApiResponse;
import com.rabbi.payload.response.AuthResponse;
import com.rabbi.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@Valid @RequestBody UserDTO req)
            throws UserException
    {
        AuthResponse res = authService.signup(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@Valid @RequestBody  LoginRequest req)
            throws UserException
    {
        AuthResponse res = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest req)
            throws UserException
    {
        authService.createPasswordResetToken(req.getEmail());

        ApiResponse res = new ApiResponse("A reset link was sent to your" +
                " email if it exists in our system.", true);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest req)
            throws Exception
    {
        authService.resetPassword(req.getToken(), req.getNewPassword());

        ApiResponse res = new ApiResponse("Password reset successful", true);
        return ResponseEntity.ok(res);

    }

}
