package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.BidListServiceException.*;
import static com.nnk.springboot.exceptions.CurvePointServiceException.*;

@ControllerAdvice
public class CurveControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CurveControllerAdvice.class);

    /**
     * Handles {@link CurvePointAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link CurvePointAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(CurvePointAggregationInfoException.class)
    public String handleCurvePointAggregationInfoException(CurvePointAggregationInfoException ex) {
        logger.info("Handling CurvePointAggregationInfoException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link CurvePointSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link CurvePointSaveException} to handle
     * @return the exception message or the cause message if it is a {@link CurvePointIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(CurvePointSaveException.class)
    public String handleCurvePointSaveException(CurvePointSaveException ex) {
        logger.info("Handling CurvePointSaveException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    @ExceptionHandler(CurvePointUpdateException.class)
    public String handleCurvePointUpdateException(CurvePointUpdateException ex) {
        logger.info("Handling CurvePointSaveException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof CurvePointIncoherenceBetweenObjectException) {
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
     * Handles {@link CurvePointFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link CurvePointFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link BidListNotFoundException}
     */
    @ExceptionHandler(CurvePointFindByIdException.class)
    public String handleCurvePointFindByIdException(CurvePointFindByIdException ex) {
        logger.info("Handling CurvePointFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof CurvePointNotFoundException) {
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
     * Handles {@link CurvePointDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link CurvePointDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(CurvePointDeleteException.class)
    public String handleCurvePointDeleteException(CurvePointDeleteException ex) {
        logger.info("Handling CurvePointDeleteException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))
    }
}
