package com.nnk.springboot.controllers.rating;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class RatingControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/home/rating/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<th>MoodysRating</th>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<th>FitchRating</th>")));
    }

    @Test
    void addRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate_true() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "AAA")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/rating/list"));
    }

    @Test
    void validate_false() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "AAA")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">orderNumber cant be null</p>")));
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"sandPRating\" placeholder=\"SandPRating\" type=\"number\" name=\"sandPRating\" value=\"sandprating\"")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"order\" placeholder=\"FitchRating\" type=\"number\" name=\"orderNumber\" value=\"12\">")));
    }

    @Test
    void updateRating_Success() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 1)
                        .param("moodysRating", "AAA")
                        .param("sandPRating", "AAA")
                        .param("fitchRating", "AAA")
                        .param("orderNumber", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/rating/list"));
    }

    @Test
    void deleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/rating/list"));
    }
}
