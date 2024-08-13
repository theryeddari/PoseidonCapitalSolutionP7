package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest() {
        // Create a new User instance
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("testUser", user.getUsername());

        // Update
        user.setUsername("updatedUser");
        user = userRepository.save(user);
        assertEquals("updatedUser", user.getUsername());

        // Find
        List<User> listResult = userRepository.findAll();
        assertFalse(listResult.isEmpty());

        // Delete
        Integer id = Integer.valueOf(user.getId());
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        assertFalse(userList.isPresent());
    }
}