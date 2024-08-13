package com.nnk.springboot.controllers.trade;

import com.nnk.springboot.domain.Trade;
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


    @Test
    void home() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>bob</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>user</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>10.0</td>")));
    }

    @Test
    void addTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate_true() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("user");
        trade.setType("USER");
        trade.setBuyQuantity(1000.0); // Adjust field as needed

        mockMvc.perform(post("/trade/validate")
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity())) // Adjust field as needed
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void validate_false() throws Exception {
        Trade trade = new Trade();
        trade.setAccount(null);
        trade.setType("user");
        trade.setBuyQuantity(0.0);
        mockMvc.perform(post("/trade/validate")
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">must not be null</p>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"account\" placeholder=\"Account\" class=\"col-4\" name=\"account\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"type\" placeholder=\"Type\" class=\"col-4\" name=\"type\" value=\"user\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">This field must have a positive number</p>"))); // Adjust field as needed
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"account\" placeholder=\"Account\" class=\"col-4\" name=\"account\" value=\"bob\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"type\" placeholder=\"Type\" class=\"col-4\" name=\"type\" value=\"user\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" step=\"0.01\" id=\"buyQuantity\" placeholder=\"Buy Quantity\" class=\"col-4\" name=\"buyQuantity\" value=\"10.0\">"))); // Adjust field as needed
    }

    @Test
    void updateTrade_Success() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);
        trade.setAccount("user");
        trade.setType("USER");
        trade.setBuyQuantity(1000.0);

        mockMvc.perform(post("/trade/update/{id}", 1)
                        .param("tradeId", String.valueOf(trade.getTradeId()))
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity())) // Adjust field as needed
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void deleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }
}
