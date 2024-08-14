package com.nnk.springboot.controllers.user;

import com.nnk.springboot.domain.User;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user", roles = "USER")
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>user</td>")));
    }

    @Test
    void addUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validateUser_true() throws Exception {
        User user = new User();
        user.setUsername("newUser");
        user.setFullname("New User");
        user.setPassword("Passw0rd!"); // Assume validation 8 char maj min special char

        mockMvc.perform(post("/user/validate")
                        .param("username", user.getUsername())
                        .param("fullname", user.getFullname())
                        .param("password", user.getPassword())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test
    void validateUser_false() throws Exception {
        // Simulate validation errors
        mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("fullname", "User")
                        .param("password", "")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">Username is mandatory</p>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">Password is mandatory<br />&quot;Password must be at least 8 characters long, include at least one uppercase letter, one digit, and one special character&quot;</p>")));
    }

    @Test
    void showUpdateForm() throws Exception {
        // Suppose you have a user in your test database with ID 1
        mockMvc.perform(get("/user/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"username\" placeholder=\"User Name\" class=\"col-4\" name=\"username\" value=\"admin\">")));
    }

    @Test
    void updateUser_Success() throws Exception {
        User user = new User();
        user.setUsername("updatedUser");
        user.setFullname("Updated User");
        user.setPassword("UpdatePassw0rd!");

        mockMvc.perform(post("/user/update/{id}", 1)
                        .param("username", user.getUsername())
                        .param("fullname", user.getFullname())
                        .param("password", user.getPassword())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test
    void deleteUser() throws Exception {
        // Suppose you have a user in your test database with ID 1
        mockMvc.perform(get("/user/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }
}