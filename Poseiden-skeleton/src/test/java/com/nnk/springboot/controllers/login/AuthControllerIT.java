package com.nnk.springboot.controllers.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests a successful login with a real user.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    public void testRealUserLoginSuccess() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("user")
                        .password("password"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("user"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests a failed login attempt with a real user.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    public void testRealUserLoginFailure() throws Exception {
        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("realUser")
                        .password("wrongPassword"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=true"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests access to a secured page after a successful login.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    public void testAccessProtectedPageAfterLogin() throws Exception {
        // Successful login
        MvcResult loginSuccessAdmin = mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("admin")
                        .password("password"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andReturn();
        // get previous session cookie to continue to be connect as admin

        MockHttpSession sessionAdmin = (MockHttpSession) loginSuccessAdmin.getRequest().getSession();

        // Accessing a secured page after login
        assert sessionAdmin != null;
        mockMvc.perform(MockMvcRequestBuilders.get("/home/admin/user/list")
                        .session(sessionAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"));
    }

    /**
     * Tests access denied for an authenticated but unauthorized user.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    public void testAccessDeniedForUnauthorizedUser() throws Exception {
        // Successful login
        MvcResult loginSuccessAdmin = mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
                        .user("user")
                        .password("password"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andReturn();
        // get previous session cookie to continue to be connect as user

        MockHttpSession sessionAdmin = (MockHttpSession) loginSuccessAdmin.getRequest().getSession();

        // Accessing a secured page after login
        assert sessionAdmin != null;
        mockMvc.perform(MockMvcRequestBuilders.get("/home/admin/user/list")
                        .session(sessionAdmin))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/403"));
    }

    /**
     * Tests redirection of an unauthenticated user to the login page.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    public void testUnauthenticatedUserRedirectToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }
}
