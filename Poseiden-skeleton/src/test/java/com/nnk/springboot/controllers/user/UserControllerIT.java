package com.nnk.springboot.controllers.user;

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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"username\" placeholder=\"User Name\" class=\"col-4\" name=\"username\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"fullname\" placeholder=\"Full Name\" class=\"col-4\" name=\"fullname\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"password\" id=\"password\" placeholder=\"Password\" class=\"col-4\" name=\"password\" value=\"\">")));
    }

    @Test
    void validateUser_true() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "newUser")
                        .param("fullname", "New User")
                        .param("password", "Passw0rd!")
                        .param("role", "USER")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test
    void validateUser_false() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "") // Missing fields
                        .param("fullname", "User")
                        .param("password", "Passw0rd")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">Username is mandatory</p>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">&quot;Password must be at least 8 characters long, include at least one uppercase letter, one digit, and one special character&quot;</p>")));
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"username\" placeholder=\"User Name\" class=\"col-4\" name=\"username\" value=\"admin\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"fullname\" placeholder=\"Full Name\" class=\"col-4\" name=\"fullname\" value=\"Administrator\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"password\" id=\"password\" placeholder=\"Password\" class=\"col-4\" name=\"password\" value=\"\">")));
    }

    @Test
    void updateUser_Success() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 1)
                        .param("username", "updatedUser")
                        .param("fullname", "Updated User")
                        .param("password", "UpdatePassw0rd!")
                        .param("role", "USER")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/list"));
    }
}
