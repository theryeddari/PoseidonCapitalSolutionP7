package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller handles requests related to login operations.
 *
 * It uses Spring Security to manage authentication and authorization.
 * The / and /login endpoints are mapped to this controller to return the login page.
 *
 * <p>
 * The {@code loginPage} method returns the login view.
 * The {@code loginPageErrorValidate} method returns the login view with an error message if login validation fails.
 * </p>
 *
 * Spring Security configuration is expected to be set up in a configuration class to secure the endpoints.
 * For detailed security setup, refer to the Spring Security documentation.
 *
 * Logging is implemented to monitor the application's behavior and catch errors.
 *
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Handles GET requests to the root URL ("/") and returns the login page view.
     *
     * @return the name of the login page view.
     */
    @GetMapping("/")
    public String loginPage() {
        logger.info("Accessing login page");
        return "login";
    }

    /**
     * Handles GET requests to the "/login" URL with an optional error parameter.
     * Adds the error message to the model and returns the login page view.
     *
     * @param error the error message to be displayed on the login page.
     * @param model the model to which the error message is added.
     * @return the name of the login page view.
     */
    @GetMapping("/login")
    public String loginPageErrorValidate(String error, Model model) {
        logger.info("Accessing login page with error: {}", error);
        model.addAttribute("error", error);
        return "login";
    }
}