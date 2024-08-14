package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.UserServiceException.*;

@ControllerAdvice
public class UserControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerAdvice.class);

    /**
     * Handles {@link UserAggregationInfoException} by logging the error and returning a user-friendly message.
     *
     * @param ex the {@link UserAggregationInfoException} to handle
     * @return a user-friendly error message
     */
    @ExceptionHandler(UserAggregationInfoException.class)
    public String handleUserAggregationInfoException(UserAggregationInfoException ex) {
        logger.info("Handling UserAggregationInfoException");
        logger.debug("Exception details: ", ex);
        // TODO: Complete logic for Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        // TODO: Consider redirecting to a specific error page or view
        return message;
    }

    /**
     * Handles {@link UserSaveException} by logging the error and returning an appropriate message.
     *
     * @param ex the {@link UserSaveException} to handle
     * @return a user-friendly error message
     */
    @ExceptionHandler(UserSaveException.class)
    public String handleUserSaveException(UserSaveException ex) {
        logger.info("Handling UserSaveException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof UserIncoherenceBetweenObjectException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            // TODO: Complete logic for Controller Advice response and adapt template to manage it
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        // TODO: Consider redirecting to a specific error page or view
        return message;
    }

    /**
     * Handles {@link UserFindByIdException} by logging the error and returning an appropriate message.
     *
     * @param ex the {@link UserFindByIdException} to handle
     * @return a user-friendly error message
     */
    @ExceptionHandler(UserFindByIdException.class)
    public String handleUserFindByIdException(UserFindByIdException ex) {
        logger.info("Handling UserFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof UserNotFoundException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            // TODO: Complete logic for Controller Advice response and adapt template to manage it
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        // TODO: Consider redirecting to a specific error page or view
        return message;
    }

    /**
     * Handles {@link UserDeleteException} by logging the error and returning a user-friendly message.
     *
     * @param ex the {@link UserDeleteException} to handle
     * @return a user-friendly error message
     */
    @ExceptionHandler(UserDeleteException.class)
    public String handleUserDeleteException(UserDeleteException ex) {
        logger.info("Handling UserDeleteException");
        logger.debug("Exception details: ", ex);
        // TODO: Complete logic for Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        // TODO: Consider redirecting to a specific error page or view
        return message;
    }
}