package com.nnk.springboot.controllers.rating;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.exceptions.RatingServiceException.*;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @InjectMocks
    RatingController ratingController;

    @Mock
    RatingService ratingService;

    @Mock
    Model model;

    @Test
    void testHome() throws RatingAggregationInfoException {
        RatingsResponse ratingsResponse = new RatingsResponse();

        when(ratingService.ratingAggregationInfo()).thenReturn(ratingsResponse);

        String viewName = ratingController.home(model);

        verify(ratingService, times(1)).ratingAggregationInfo();

        assertEquals("rating/list", viewName);

        verify(model, times(1)).addAttribute(eq("ratings"), eq(ratingsResponse.getRatingResponseAggregationInfoDTO()));
    }

    @Test
    void testAddRatingForm() {
        Rating rating = new Rating();
        String viewName = ratingController.addRatingForm(rating, model);

        assertEquals("rating/add", viewName);
        verify(model, times(1)).addAttribute(eq("rating"), eq(rating));
    }

    @Test
    void testValidate_True() throws RatingSaveException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        when(ratingService.ratingSave(rating)).thenReturn(rating);

        String viewName = ratingController.validate(rating, bindingResult, model);

        assertEquals("redirect:/home/rating/list", viewName);
        verify(ratingService, times(1)).ratingSave(rating);
        verify(model, never()).addAttribute(eq("rating"), eq(rating));
    }

    @Test
    void testValidate_False() throws RatingSaveException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        bindingResult.rejectValue("sandPRating", "error.rating", "Account is required");

        String viewName = ratingController.validate(rating, bindingResult, model);

        assertEquals("rating/add", viewName);
        verify(model, times(1)).addAttribute(eq("rating"), eq(rating));

        verify(ratingService, never()).ratingSave(rating);
    }

    @Test
    void testShowUpdateForm() throws RatingFindByIdException {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        when(ratingService.ratingFindById(1)).thenReturn(rating);

        String viewName = ratingController.showUpdateForm(1, model);

        verify(ratingService, times(1)).ratingFindById(1);

        assertEquals("rating/update", viewName);
        verify(model, times(1)).addAttribute(eq("rating"), eq(rating));
    }

    @Test
    void testUpdateRating_True() throws RatingUpdateException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        doNothing().when(ratingService).ratingUpdate(1, rating);

        String viewName = ratingController.updateRating(1, rating, bindingResult, model);

        assertEquals("redirect:/home/rating/list", viewName);
        verify(ratingService, times(1)).ratingUpdate(1, rating);
    }

    @Test
    void testUpdateRating_False() throws RatingUpdateException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        bindingResult.rejectValue("sandPRating", "error.rating", "Account is required");

        String viewName = ratingController.updateRating(1, rating, bindingResult, model);

        assertEquals("rating/update", viewName);
        verify(model, times(1)).addAttribute(eq("rating"), eq(rating));
        verify(ratingService, never()).ratingUpdate(1, rating);
    }

    @Test
    void testDeleteRating() throws RatingDeleteException {
        doNothing().when(ratingService).ratingDelete(1);

        String viewName = ratingController.deleteRating(1);

        verify(ratingService, times(1)).ratingDelete(1);
        assertEquals("redirect:/home/rating/list", viewName);
    }
}
