package com.example.simpleblogapp.config;

import com.example.simpleblogapp.model.User;
import com.example.simpleblogapp.repositories.UserRepository;
import com.example.simpleblogapp.utils.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initSuperAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "superadmin@example.com";

            if (userRepository.findByEmail(email).isEmpty()) {
                User superAdmin = new User();
                superAdmin.setName("Super Admin");
                superAdmin.setEmail(email);
                superAdmin.setPassword(passwordEncoder.encode("secret"));
                superAdmin.setRole(Role.SUPERADMIN);

                userRepository.save(superAdmin);
                System.out.println("Default SUPERADMIN created: " + email + " / secret");
            } else {
                System.out.println("SUPERADMIN already exists, skipping...");
            }
        };
    }
}

