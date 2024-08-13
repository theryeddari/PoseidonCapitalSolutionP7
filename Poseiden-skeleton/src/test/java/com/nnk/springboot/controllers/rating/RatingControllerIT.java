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

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>moodysrating</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td style=\"width: 10%\">1</td>")));
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"moodysRating\" placeholder=\"MoodysRating\" class=\"col-4\" name=\"moodysRating\" value=\"AAA\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"sandPRating\" placeholder=\"SandPRating\" class=\"col-4\" name=\"sandPRating\" value=\"AAA\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"order\" placeholder=\"FitchRating\" class=\"col-4\" name=\"orderNumber\" value=\"1\">")));
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
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">This Field can&#39;t be null</p>")))
;
    }

    @Test
    void showUpdateForm() throws Exception {

        mockMvc.perform(get("/rating/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"moodysRating\" placeholder=\"MoodysRating\" class=\"col-4\" name=\"moodysRating\" value=\"moodysrating\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"sandPRating\" placeholder=\"SandPRating\" class=\"col-4\" name=\"sandPRating\" value=\"sandprating\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"fitchRating\" placeholder=\"FitchRating\" class=\"col-4\" name=\"fitchRating\" value=\"fitchrating\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"order\" placeholder=\"FitchRating\" class=\"col-4\" name=\"orderNumber\" value=\"12\">")));
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
                .andExpect(header().string("Location", "/rating/list"));
    }

    @Test
    void deleteRating() throws Exception {

        mockMvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));

    }
}
