package com.nnk.springboot.controllers;

import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameAggregationInfoException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameSaveException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameUpdateException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameFindByIdException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameDeleteException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameIncoherenceBetweenObjectException;
import com.nnk.springboot.exceptions.RuleNameServiceException.RuleNameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuleNameControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RuleNameControllerAdvice.class);

    /**
     * Handles {@link RuleNameAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link RuleNameAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(RuleNameAggregationInfoException.class)
    public String handleRuleNameAggregationInfoException(RuleNameAggregationInfoException ex) {
        logger.info("Handling RuleNameAggregationInfoException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link RuleNameSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link RuleNameSaveException} to handle
     * @return the exception message or the cause message if it is a {@link RuleNameIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(RuleNameSaveException.class)
    public String handleRuleNameSaveException(RuleNameSaveException ex) {
        logger.info("Handling RuleNameSaveException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link RuleNameUpdateException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link RuleNameUpdateException} to handle
     * @return the exception message or the cause message if it is a {@link RuleNameIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(RuleNameUpdateException.class)
    public String handleRuleNameUpdateException(RuleNameUpdateException ex) {
        logger.info("Handling RuleNameUpdateException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof RuleNameIncoherenceBetweenObjectException) {
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
     * Handles {@link RuleNameFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link RuleNameFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link RuleNameNotFoundException}
     */
    @ExceptionHandler(RuleNameFindByIdException.class)
    public String handleRuleNameFindByIdException(RuleNameFindByIdException ex) {
        logger.info("Handling RuleNameFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof RuleNameNotFoundException) {
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
     * Handles {@link RuleNameDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link RuleNameDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(RuleNameDeleteException.class)
    public String handleRuleNameDeleteException(RuleNameDeleteException ex) {
        logger.info("Handling RuleNameDeleteException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }
}
