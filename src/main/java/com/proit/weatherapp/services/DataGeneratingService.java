package com.proit.weatherapp.services;

import com.proit.weatherapp.entity.Role;
import com.proit.weatherapp.entity.User;
import com.proit.weatherapp.entity.UserRepository;
import com.proit.weatherapp.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DataGeneratingService {
    private static final Logger logger = LoggerFactory.getLogger(DataGeneratingService.class);


    private final UserRepository userRepository;

    public DataGeneratingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void generateUser() throws IOException {
        logger.info("Generating initial data...");

        // Inserting David Bowie with role USER
        User user = new User();
        user.setUsername("david");
        user.setName("David Bowie");

        ClassPathResource resource = new ClassPathResource("user_data/david.jpeg");
        Path path = Paths.get(resource.getURI());
        byte[] profilePicture = Files.readAllBytes(path);
        user.setProfilePicture(profilePicture);

        String passwordHash = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setHashedPassword(passwordHash);
        user = userRepository.saveAndFlush(user);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.USER);
        user.getRoles().add(userRole);
        userRepository.saveAndFlush(user);

        // Inserting John Cena with role ADMIN
        user = new User();
        user.setUsername("john");
        user.setName("John Cena");

        resource = new ClassPathResource("user_data/john.png");
        path = Paths.get(resource.getURI());
        profilePicture = Files.readAllBytes(path);
        user.setProfilePicture(profilePicture);

        passwordHash = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setHashedPassword(passwordHash);
        user = userRepository.saveAndFlush(user);
        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.USER);
        user.getRoles().add(userRole);
        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.ADMIN);
        user.getRoles().add(userRole);
        userRepository.saveAndFlush(user);

        // Inserting Emma Stone with role USER
        user = new User();
        user.setUsername("emma");
        user.setName("Emma Stone");

        resource = new ClassPathResource("user_data/emma.jpeg");
        path = Paths.get(resource.getURI());
        profilePicture = Files.readAllBytes(path);
        user.setProfilePicture(profilePicture);

        passwordHash = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setHashedPassword(passwordHash);
        user = userRepository.saveAndFlush(user);
        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.USER);
        user.getRoles().add(userRole);
        userRepository.saveAndFlush(user);

        // Inserting Olivia Rodrigo with role USER
        user = new User();
        user.setUsername("olivia");
        user.setName("Olivia Rodrigo");

        resource = new ClassPathResource("user_data/olivia.jpeg");
        path = Paths.get(resource.getURI());
        profilePicture = Files.readAllBytes(path);
        user.setProfilePicture(profilePicture);

        passwordHash = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setHashedPassword(passwordHash);
        user = userRepository.saveAndFlush(user);
        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.USER);
        user.getRoles().add(userRole);
        userRepository.saveAndFlush(user);

        logger.info("Initial data generated");
    }
}
