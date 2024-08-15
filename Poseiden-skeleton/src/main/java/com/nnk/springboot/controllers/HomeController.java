package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller for handling requests related to the homepage and
 * administrative redirections.
 */
@Controller
public class HomeController {

	/**
	 * Handles requests for the homepage of the site.
	 *
	 * @return The name of the view for the homepage, typically used by the template engine
	 *         (e.g., Thymeleaf) to render the corresponding view.
	 */
	@RequestMapping("/home")
	public String home() {
		return "home"; // Name of the view (e.g., home.html)
	}

}