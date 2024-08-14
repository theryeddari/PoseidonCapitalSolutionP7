package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserResponse;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.nnk.springboot.exceptions.UserServiceException.*;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Displays the list of users.
     *
     * @param model the model to populate with users
     * @return the view name for the user list
     */
    @RequestMapping("/user/list")
    public String home(Model model) throws UserAggregationInfoException {
        logger.info("Received request to list users");
        UserResponse userResponse = userService.userAggregationInfo();
        model.addAttribute("users", userResponse.getUserResponseAggregationInfoDTO());
        logger.info("Users successfully retrieved and added to the model");
        return "user/list";
    }

    /**
     * Displays the form for adding a new user.
     *
     * @param user the user to add
     * @param model the model to populate with the user
     * @return the view name for adding a user
     */
    @GetMapping("/user/add")
    public String addUserForm(User user, Model model) {
        logger.info("Received request to show add user form");
        model.addAttribute("user", user);
        logger.info("Add user form displayed");
        return "user/add";
    }

    /**
     * Validates and saves the user.
     *
     * @param user the user to save
     * @param result the result of validation
     * @param model the model to populate with the user
     * @return the view name for the add user form or redirect to the list
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) throws UserSaveException {
        logger.info("Received request to validate and save user");
        if (result.hasErrors()) {
            logger.info("User validation failed");
            model.addAttribute("user", user);
            return "user/add";
        }
        userService.userSave(user);
        logger.info("User successfully validated and saved");
        return "redirect:/user/list";
    }

    /**
     * Displays the form for updating an existing user.
     *
     * @param id the ID of the user to update
     * @param model the model to populate with the user
     * @return the view name for updating the user
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws UserFindByIdException {
        logger.info("Received request to show update form for user with ID: {}", id);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        logger.info("Update form for user with ID: {} displayed", id);
        return "user/update";
    }

    /**
     * Updates the specified user.
     *
     * @param id the ID of the user to update
     * @param user the user data to update
     * @param result the result of validation
     * @param model the model to populate with the updated user
     * @return the redirect URL for the user list
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) throws UserSaveException {
        logger.info("Received request to update user with ID: {}", id);
        if (result.hasErrors()) {
            logger.info("User validation failed");
            model.addAttribute("user", user);
            return "user/update";
        }
        userService.userUpdate(id, user);
        logger.info("User with ID: {} successfully updated", id);
        return "redirect:/user/list";
    }

    /**
     * Deletes the specified user.
     *
     * @param id the ID of the user to delete
     * @return the redirect URL for the user list
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) throws UserDeleteException {
        logger.info("Received request to delete user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("User with ID: {} successfully deleted", id);
        return "redirect:/user/list";
    }
}