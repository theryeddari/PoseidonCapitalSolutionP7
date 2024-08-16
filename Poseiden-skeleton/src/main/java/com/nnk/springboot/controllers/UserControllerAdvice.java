package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.UserServiceException.*;

@ControllerAdvice
public class UserControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerAdvice.class);

    /**
     * Handles {@link UserAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link UserAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(UserAggregationInfoException.class)
    public String handleUserAggregationInfoException(UserAggregationInfoException ex) {
        logger.info("Handling UserAggregationInfoException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link UserSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception. or view for error
     *
     * @param ex the {@link UserSaveException} to handle
     * @return the exception message or the cause message if it is a {@link UserIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(UserSaveException.class)
    public String handleUserSaveException(UserSaveException ex, Model model) {
        logger.info("Handling UserSaveException");
        logger.debug("Exception details: ", ex);

        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        if (ex.getCause() instanceof UsernameAlreadyExistException) {

            bindingResult.rejectValue("username", "error.user", ex.getCause().getMessage());

            model.addAttribute("org.springframework.validation.BindingResult.user", bindingResult);

            model.addAttribute("user", user);

            return "user/add"; // Le nom de votre vue
        }

        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link UserUpdateException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link UserUpdateException} to handle
     * @return the exception message or the cause message if it is a {@link UserIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(UserUpdateException.class)
    public String handleUserUpdateException(UserUpdateException ex) {
        logger.info("Handling UserUpdateException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof UserIncoherenceBetweenObjectException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link UserFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link UserFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link UserNotFoundException}
     */
    @ExceptionHandler(UserFindByIdException.class)
    public String handleUserFindByIdException(UserFindByIdException ex) {
        logger.info("Handling UserFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof UserNotFoundException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link UserDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link UserDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(UserDeleteException.class)
    public String handleUserDeleteException(UserDeleteException ex) {
        logger.info("Handling UserDeleteException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }
}
