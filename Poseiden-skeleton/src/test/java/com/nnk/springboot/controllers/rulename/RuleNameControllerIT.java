package com.nnk.springboot.controllers.rulename;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>name</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>description</td>")));

    }

    @Test
    void addRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\" class=\"col-4\" name=\"name\" value=\"\">")));
    }

    @Test
    void validate_true() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Example Rule");
        ruleName.setDescription("Example Description");
        ruleName.setJson("Example JSON");

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\" class=\"col-4\" name=\"name\" value=\"Example Rule\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"description\" placeholder=\"Description\" class=\"col-4\" name=\"description\" value=\"Example Description\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"json\" placeholder=\"Json\" class=\"col-4\" name=\"json\" value=\"Example JSON\">")));
    }

//    @Test
//    void validate_false() throws Exception {
//        mockMvc.perform(post("/ruleName/validate")
//                        .param("name", "") // Missing mandatory fields
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">must not be empty</p>")));
//    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"name\" placeholder=\"Name\" class=\"col-4\" name=\"name\" value=\"name\">")));
    }

    @Test
    void updateRuleName_Success() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Updated Rule");
        ruleName.setDescription("Updated Description");
        ruleName.setJson("Updated JSON");

        mockMvc.perform(post("/ruleName/update/{id}", 1)
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @Test
    void deleteRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }
}
