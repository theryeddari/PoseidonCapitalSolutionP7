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

        //check if ExistByName while no exist
        boolean resultNoExist = userRepository.existsByUsername(user.getUsername());
        assertFalse(resultNoExist);

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("testUser", user.getUsername());

        //check if ExistByName while exist
        boolean resultExist = userRepository.existsByUsername(user.getUsername());
        assertTrue(resultExist);

        //Find By name and check it with user saved
        User user1 = userRepository.findByUsername(user.getUsername()).orElse(null);
        assert user1 != null;
        assertEquals(user.getId(), user1.getId());

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