package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.services.RatingService;
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

import static com.nnk.springboot.exceptions.RatingServiceException.*;

/**
 * Controller for managing Rating operations.
 */
@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * Displays the list of ratings.
     *
     * @param model the model to populate with ratings
     * @return the view name for the rating list
     * @throws RatingAggregationInfoException if there is an error aggregating rating info
     */
    @RequestMapping("/rating/list")
    public String home(Model model) throws RatingAggregationInfoException {
        logger.info("Received request to list ratings");
        RatingsResponse ratingsResponse = ratingService.ratingAggregationInfo();
        model.addAttribute("ratings", ratingsResponse.getRatingResponseResponseAggregationInfoDTO());
        logger.info("Ratings successfully retrieved and added to the model");
        return "rating/list";
    }

    /**
     * Displays the form for adding a new rating.
     *
     * @param rating the rating to add
     * @param model  the model to populate with the rating
     * @return the view name for adding a rating
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        logger.info("Received request to show add rating form");
        model.addAttribute("rating", rating);
        logger.info("Add rating form displayed");
        return "rating/add";
    }

    /**
     * Validates and saves the rating.
     *
     * @param rating the rating to save
     * @param result the result of validation
     * @param model  the model to populate with the rating
     * @return the view name for the add rating form or redirect to the list
     * @throws RatingSaveException if there is an error saving the rating
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) throws RatingSaveException {
        logger.info("Received request to validate and save rating");
        rating = ratingService.ratingSave(rating, result);
        model.addAttribute("rating", rating);
        logger.info("Rating successfully validated and saved");
        return "rating/add";
    }

    /**
     * Displays the form for updating an existing rating.
     *
     * @param id    the ID of the rating to update
     * @param model the model to populate with the rating
     * @return the view name for updating the rating
     * @throws RatingFindByIdException if there is an error finding the rating by ID
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RatingFindByIdException {
        logger.info("Received request to show update form for rating with ID: {}", id);
        Rating rating = ratingService.ratingFindById(id);
        model.addAttribute("rating", rating);
        logger.info("Update form for rating with ID: {} displayed", id);
        return "rating/update";
    }

    /**
     * Updates the specified rating.
     *
     * @param id      the ID of the rating to update
     * @param rating  the rating data to update
     * @param result  the result of validation
     * @param model   the model to populate with the updated rating
     * @return the redirect URL for the rating list
     * @throws RatingSaveException if there is an error saving the updated rating
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) throws RatingSaveException {
        logger.info("Received request to update rating with ID: {}", id);
        rating = ratingService.ratingSave(id, rating, result);
        model.addAttribute("rating", rating);
        logger.info("Rating with ID: {} successfully updated", id);
        return "redirect:/rating/list";
    }

    /**
     * Deletes the specified rating.
     *
     * @param id the ID of the rating to delete
     * @return the redirect URL for the rating list
     * @throws RatingDeleteException if there is an error deleting the rating
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) throws RatingDeleteException {
        logger.info("Received request to delete rating with ID: {}", id);
        ratingService.ratingDelete(id);
        logger.info("Rating with ID: {} successfully deleted", id);
        return "redirect:/rating/list";
    }
}
