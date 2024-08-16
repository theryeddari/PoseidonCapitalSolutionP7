package com.nnk.springboot.controllers.login;

import com.nnk.springboot.controllers.AuthController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private Model model;


    @Test
    public void testLoginPageErrorValidateWithoutError() {
        // Call the method to test without error parameter
        String viewName = authController.loginPageErrorValidate(null, model);

        // Assert that the view name is "login"
        assertEquals("login", viewName);

        // Verify that the model attribute "error" was not added
        verify(model, never()).addAttribute("error", "true");
    }

    @Test
    public void testLoginPageErrorValidateWithError() {
        // Call the method to test with error parameter
        String viewName = authController.loginPageErrorValidate("true", model);

        // Assert that the view name is "login"
        assertEquals("login", viewName);

        // Verify that the model attribute "error" was added with value "true"
        verify(model).addAttribute("error", "true");
    }
}