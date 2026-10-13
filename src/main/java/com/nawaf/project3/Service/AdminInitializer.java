package com.nawaf.project3.Config;

import com.nawaf.project3.Model.User;
import com.nawaf.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (authRepository.findUserByUsername("admin") != null) return;

        User admin = new User(
                null,
                "admin",
                passwordEncoder.encode("Admin123!"),
                "System Admin",
                "admin@example.com",
                "ADMIN",
                null,
                null
        );

        authRepository.save(admin);
    }
}