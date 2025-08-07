package com.example.LibraryTracker.config.security;

import com.example.LibraryTracker.entity.User;
import com.example.LibraryTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0){

            User admin = new User();
            admin.setUsername("omerkenar");
            admin.setPassword(passwordEncoder.encode("admin1"));
            admin.setEmail("omer@omer.com");
            admin.setRoles(Set.of("ADMIN"));
            admin.setEnabled(true);
            admin.setAccountNonLocked(true);
            userRepository.save(admin);

            User user1 = new User();
            user1.setUsername("aliveli");
            user1.setPassword(passwordEncoder.encode("123"));
            user1.setEmail("ali@veli.com");
            user1.setRoles(Set.of("USER"));
            user1.setEnabled(true);
            user1.setAccountNonLocked(true);
            userRepository.save(user1);

            log.info(">>> 1 Admin + 1 User is created!");
        } else {
            log.error(">>> DB is already initialized!");
        }
    }
}
