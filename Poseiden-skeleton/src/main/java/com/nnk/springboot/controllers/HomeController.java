package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller for handling requests related to the homepage and
 * administrative redirections.
 */
@Controller
public class HomeController {
		private static final Logger logger = LogManager.getLogger(HomeController.class);

	/**
	 * Handles requests for the homepage of the site.
	 *
	 * @return The name of the view for the homepage, typically used by the template engine
	 *         (e.g., Thymeleaf) to render the corresponding view.
	 */
	@RequestMapping("/home")
	public String home() {
		logger.info("Received request to go home");
		return "home"; // Name of the view (e.g., home.html)
	}

}