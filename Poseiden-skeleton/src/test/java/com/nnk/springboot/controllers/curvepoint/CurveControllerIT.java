package com.nnk.springboot.controllers.curvepoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
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
public class CurveControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void home() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>1</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>10.0</td>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>20.0</td>")));
    }
    @Test
    void addCurveForm() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    void validate_true() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10d);
        curvePoint.setValue(20D);

        mockMvc.perform(post("/curvePoint/validate")
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"text\" id=\"term\" placeholder=\"Term\" class=\"col-4\" name=\"term\" value=\"10.0\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"value\" placeholder=\"Value\" class=\"col-4\" name=\"value\" value=\"20.0\">")));
    }
    @Test
    void validate_false() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setValue(20D);

        mockMvc.perform(post("/curvePoint/validate")
                        .param("term", "")
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" step=\"0.01\" id=\"term\" placeholder=\"Term\" class=\"col-4\" name=\"term\" value=\"\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" step=\"0.01\" id=\"value\" placeholder=\"Value\" class=\"col-4\" name=\"value\" value=\"20.0\">")));

    }

    @Test
    void showUpdateForm() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"term\" placeholder=\"Term\" class=\"col-4\" name=\"term\" value=\"10.0\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<input type=\"number\" id=\"value\" placeholder=\"Value\" class=\"col-4\" name=\"value\" value=\"20.0\">")));
    }

    @Test
    void updateBidOverloadWithIdVerification_Success() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        curvePoint.setTerm(10d);
        curvePoint.setValue(20D);

        mockMvc.perform(post("/curvePoint/update/{id}",1)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }
    @Test
    void deleteBid() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));

    }

}
