package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.RatingServiceException.*;

/**
 * ControllerAdvice class for handling exceptions in the Rating service.
 */
@ControllerAdvice
public class RatingControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RatingControllerAdvice.class);

    /**
     * Handles {@link RatingAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link RatingAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(RatingAggregationInfoException.class)
    public String handleRatingAggregationInfoException(RatingAggregationInfoException ex) {
        logger.info("Handling RatingAggregationInfoException");
        logger.debug("Exception details: ", ex);
        // TODO: complete logic Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link RatingSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link RatingSaveException} to handle
     * @return the exception message or the cause message if it is a {@link RatingIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(RatingSaveException.class)
    public String handleRatingSaveException(RatingSaveException ex) {
        logger.info("Handling RatingSaveException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof RatingIncoherenceBetweenObjectException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link RatingFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link RatingFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link RatingNotFoundException}
     */
    @ExceptionHandler(RatingFindByIdException.class)
    public String handleRatingFindByIdException(RatingFindByIdException ex) {
        logger.info("Handling RatingFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof RatingNotFoundException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link RatingDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link RatingDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(RatingDeleteException.class)
    public String handleRatingDeleteException(RatingDeleteException ex) {
        logger.info("Handling RatingDeleteException");
        logger.debug("Exception details: ", ex);
        // TODO: complete logic Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }
}