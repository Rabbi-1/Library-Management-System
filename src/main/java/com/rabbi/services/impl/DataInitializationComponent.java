package com.rabbi.services.impl;

import com.rabbi.domain.UserRole;
import com.rabbi.model.User;
import com.rabbi.repo.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminEmail = "altfornow12@gmail.com";
        String adminPassword = "password123";

        if(userRespository.findByEmail(adminEmail) == null) {
            User user = User.builder()
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .fullName("Code With Fuad")
                    .role(UserRole.ROLE_ADMIN)
                    .build();
            User admin = userRespository.save(user);

        }


    }
}
