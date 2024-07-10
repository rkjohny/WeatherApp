package com.proit.weatherapp.core;

import com.proit.weatherapp.entity.Role;
import com.proit.weatherapp.entity.User;
import com.proit.weatherapp.repository.UserRepository;
import com.proit.weatherapp.entity.UserRole;
import com.proit.weatherapp.util.Utils;
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
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void generateUser() {
        logger.info("Generating initial data...");

        // Inserting David Bowie with role USER
        User user = new User();
        user.setUsername("david");
        user.setName("David Bowie");
        user.setProfilePicture(Utils.readProfilePicture("david.jpeg"));

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
        user.setProfilePicture(Utils.readProfilePicture("john.png"));

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
        user.setProfilePicture(Utils.readProfilePicture("emma.jpeg"));

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
        user.setProfilePicture(Utils.readProfilePicture("olivia.jpg"));

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
