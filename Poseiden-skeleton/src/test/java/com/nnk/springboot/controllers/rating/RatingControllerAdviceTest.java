package com.nnk.springboot.controllers.rating;

import com.nnk.springboot.controllers.RatingControllerAdvice;
import com.nnk.springboot.exceptions.RatingServiceException.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

@ExtendWith(MockitoExtension.class)
public class RatingControllerAdviceTest {

    RatingControllerAdvice ratingControllerAdvice = new RatingControllerAdvice();

    @Test
    void handleRatingAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = ratingControllerAdvice.handleRatingAggregationInfoException(new RatingAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(RATING_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRatingSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = ratingControllerAdvice.handleRatingSaveException(new RatingSaveException(runtimeException));

        Assertions.assertTrue(result.contains(RATING_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRatingFindByIdException() {
        String result = ratingControllerAdvice.handleRatingFindByIdException(new RatingFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(RATING_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRatingFindById_RatingNotFoundException() {
        RatingNotFoundException ratingNotFoundException = new RatingNotFoundException();
        String result = ratingControllerAdvice.handleRatingFindByIdException(new RatingFindByIdException(ratingNotFoundException));

        Assertions.assertTrue(result.contains(RATING_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleRatingUpdateException() {
        String result = ratingControllerAdvice.handleRatingUpdateException(new RatingUpdateException(new RuntimeException()));

        Assertions.assertTrue(result.contains(RATING_UPDATE_EXCEPTION));
    }

    @Test
    void handleRatingUpdateException_WithIdVerification_Failed() {
        RatingIncoherenceBetweenObjectException ratingIncoherenceBetweenObjectException = new RatingIncoherenceBetweenObjectException();
        String result = ratingControllerAdvice.handleRatingUpdateException(new RatingUpdateException(ratingIncoherenceBetweenObjectException));

        Assertions.assertTrue(result.contains(RATING_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION));
    }

    @Test
    void handleRatingDeleteException() {
        RatingDeleteException ratingDeleteException = new RatingDeleteException(new RuntimeException());
        String result = ratingControllerAdvice.handleRatingDeleteException(ratingDeleteException);

        Assertions.assertTrue(result.contains(RATING_DELETE_EXCEPTION + MORE_INFO));
    }
}
