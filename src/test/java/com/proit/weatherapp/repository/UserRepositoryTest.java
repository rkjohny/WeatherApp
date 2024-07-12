package com.proit.weatherapp.repository;

import com.proit.weatherapp.entity.Role;
import com.proit.weatherapp.entity.User;
import com.proit.weatherapp.entity.UserRole;
import com.proit.weatherapp.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;


    @BeforeEach
    public void initTest() {

    }

    @Test
    void saveTest() {
        User user = new User();
        user.setUsername("reza");
        user.setName("Rezaul Karim");
        user.setProfilePicture(Utils.readProfilePicture("reza.jpg"));

        String passwordHash = BCrypt.hashpw("password", BCrypt.gensalt());
        user.setHashedPassword(passwordHash);
        user = userRepository.saveAndFlush(user);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.USER);
        user.getRoles().add(userRole);
        user = userRepository.saveAndFlush(user);

        boolean saved = userRepository.existsById(user.getId());
        assertTrue(saved);
        Optional<User> savedUser = userRepository.findById(user.getId());
        assertTrue(savedUser.isPresent());
        assertEquals(savedUser.get().getUsername(), user.getUsername());
    }
}