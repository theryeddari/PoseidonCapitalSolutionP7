package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.BidListServiceException.*;

@ControllerAdvice
public class BidListControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BidListControllerAdvice.class);

    /**
     * Handles {@link BidListAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link BidListAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(BidListAggregationInfoException.class)
    public String handleBidListAggregationInfoException(BidListAggregationInfoException ex) {
        logger.info("Handling BidListAggregationInfoException");
        logger.debug("Exception details: ", ex);
        //TODO: complete logic Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link BidListSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link BidListSaveException} to handle
     * @return the exception message or the cause message if it is a {@link BidListIncoherenceBetweenObject}
     */
    @ExceptionHandler(BidListSaveException.class)
    public String handleBidListSaveException(BidListSaveException ex) {
        logger.info("Handling BidListSaveException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof BidListIncoherenceBetweenObject) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link BidListFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link BidListFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link BidListNotFoundException}
     */
    @ExceptionHandler(BidListFindByIdException.class)
    public String handleBidListFindByIdException(BidListFindByIdException ex) {
        logger.info("Handling BidListFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof BidListNotFoundException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link BidListDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link BidListDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(BidListDeleteException.class)
    public String handleBidListDeleteException(BidListDeleteException ex) {
        logger.info("Handling BidListDeleteException");
        logger.debug("Exception details: ", ex);
        //TODO: complete logic Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }
}
