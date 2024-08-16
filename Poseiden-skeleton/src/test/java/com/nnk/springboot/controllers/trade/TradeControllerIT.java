package com.nnk.springboot.controllers.trade;

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
public class TradeControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/home/trade/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>bob</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>user</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>10.0</td>")));
    }

    @Test
    void addTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"account\" placeholder=\"Account\" type=\"text\" name=\"account\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"type\" placeholder=\"Type\" type=\"text\" name=\"type\" value=\"\"")));
    }

    @Test
    void validate_true() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "user")
                        .param("type", "USER")
                        .param("buyQuantity", "1000.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/trade/list"));
    }

    @Test
    void validate_false() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "bob") // Missing fields
                        .param("type", "user")
                        .param("buyQuantity", "0.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">buyQuantity field must have a positive number</p>")));
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"account\" placeholder=\"Account\" type=\"text\" name=\"account\" value=\"bob\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input class=\"col-4\" id=\"type\" placeholder=\"Type\" type=\"text\" name=\"type\" value=\"user\">")));
    }

    @Test
    void updateTrade_Success() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 1)
                        .param("tradeId", "1")
                        .param("account", "user")
                        .param("type", "USER")
                        .param("buyQuantity", "1000.0")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/trade/list"));
    }

    @Test
    void deleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home/trade/list"));
    }
}
