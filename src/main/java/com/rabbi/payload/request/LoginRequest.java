package com.rabbi.payload.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "Password cannot be null")
    private String password;
}
