package com.designstudio.ps.config;

import com.designstudio.ps.model.User;
import com.designstudio.ps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User client = new User();
            client.setUsername("client");
            client.setPasswordHash(passwordEncoder.encode("password"));
            client.setEmail("client@example.com");
            client.setRole(User.Role.CLIENT);
            userRepository.save(client);

            User manager = new User();
            manager.setUsername("manager");
            manager.setPasswordHash(passwordEncoder.encode("password"));
            manager.setEmail("manager@example.com");
            manager.setRole(User.Role.MANAGER);
            userRepository.save(manager);
        }
    }
}