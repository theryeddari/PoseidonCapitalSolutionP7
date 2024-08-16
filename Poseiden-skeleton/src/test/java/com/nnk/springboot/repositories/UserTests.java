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
    public void testUserCreation() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testUserExistsByUsernameWhenNoUser() {
        boolean resultNoExist = userRepository.existsByUsername("nonExistingUser");
        assertFalse(resultNoExist);
    }

    @Test
    public void testUserExistsByUsernameWhenUserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        user = userRepository.save(user);

        boolean resultExist = userRepository.existsByUsername(user.getUsername());
        assertTrue(resultExist);
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        user = userRepository.save(user);

        User user1 = userRepository.findByUsername(user.getUsername()).orElse(null);
        assertNotNull(user1);
        assertEquals(user.getId(), user1.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        user = userRepository.save(user);

        user.setUsername("updatedUser");
        user = userRepository.save(user);
        assertEquals("updatedUser", user.getUsername());
    }

    @Test
    public void testFindAllUsers() {
        List<User> listResult = userRepository.findAll();
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("P@ssw0rd");
        user.setFullname("Test User");
        user.setRole("USER");

        user = userRepository.save(user);

        Integer id = Integer.valueOf(user.getId());
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        assertFalse(userList.isPresent());
    }
}