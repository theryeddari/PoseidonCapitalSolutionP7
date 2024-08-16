package com.nnk.springboot.controllers.rulename;

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
public class RuleNameControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/home/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>name</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>description</td>")));
    }

    @Test
    void addRuleNameForm() throws Exception {
        mockMvc.perform(get("/home/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"name\" placeholder=\"Name\" type=\"text\" name=\"name\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"description\" placeholder=\"Description\" type=\"text\" name=\"description\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"json\" placeholder=\"Json\" type=\"text\" name=\"json\" value=\"\">")));
    }

    @Test
    void validate_true() throws Exception {
        mockMvc.perform(post("/home/ruleName/validate")
                        .param("name", "Example Rule")
                        .param("description", "Example Description")
                        .param("json", "Example JSON")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/ruleName/list"));
    }

    @Test
    void validate_false() throws Exception {
        mockMvc.perform(post("/home/ruleName/validate")
                        .param("name", "") // Missing fields
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">must not be blank</p>")));
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/home/ruleName/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"name\" placeholder=\"Name\" type=\"text\" name=\"name\" value=\"name\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"description\" placeholder=\"Description\" type=\"text\" name=\"description\" value=\"description\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"json\" placeholder=\"Json\" type=\"text\" name=\"json\" value=\"json\">")));
    }

    @Test
    void updateRuleName_Success() throws Exception {
        mockMvc.perform(post("/home/ruleName/update/{id}", 1)
                        .param("name", "Updated Rule")
                        .param("description", "Updated Description")
                        .param("json", "Updated JSON")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/ruleName/list"));
    }

    @Test
    void deleteRuleName() throws Exception {
        mockMvc.perform(get("/home/ruleName/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/ruleName/list"));
    }
}
