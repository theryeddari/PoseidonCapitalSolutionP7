package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nnk.springboot.domain.BidList;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user", roles = "USER")
public class BidListControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void home() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>user</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>USER</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>10.0</td>")));
    }
    @Test
    void addBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate_true() throws Exception {
        // Créez un objet BidList avec des valeurs valides pour le test
        BidList bidList = new BidList();
        bidList.setAccount("user");
        bidList.setType("USER");
        bidList.setBidQuantity(10.0);

        // Utilisez MockMvc pour envoyer une requête POST avec les données du formulaire
        mockMvc.perform(post("/bidList/validate")
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()) // Vérifiez que le statut est OK
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"account\" placeholder=\"Account\" class=\"col-4\" name=\"account\" value=\"user\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"type\" placeholder=\"Type\" class=\"col-4\" name=\"type\" value=\"USER\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"bidQuantity\" placeholder=\"Bid Quantity\" class=\"col-4\" name=\"bidQuantity\" value=\"10.0\">")));
    }
    @Test
    void validate_false() throws Exception {
        // Créez un objet BidList avec des valeurs valides pour le test
        BidList bidList = new BidList();
        bidList.setType("USER");
        bidList.setBidQuantity(10.0);

        // Utilisez MockMvc pour envoyer une requête POST avec les données du formulaire
        mockMvc.perform(post("/bidList/validate")
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()) // Vérifiez que le statut est OK
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<p class=\"text-danger\">must not be null</p>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"type\" placeholder=\"Type\" class=\"col-4\" name=\"type\" value=\"USER\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"bidQuantity\" placeholder=\"Bid Quantity\" class=\"col-4\" name=\"bidQuantity\" value=\"10.0\">")));
    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"account\" placeholder=\"Account\" class=\"col-4\" name=\"account\" value=\"user\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"type\" placeholder=\"Type\" class=\"col-4\" name=\"type\" value=\"USER\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"bidQuantity\" placeholder=\"Bid Quantity\" class=\"col-4\" name=\"bidQuantity\" value=\"10.0\">")));
    }

}
