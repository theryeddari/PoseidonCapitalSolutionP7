package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.TradeServiceException.*;

@ControllerAdvice
public class TradeControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(TradeControllerAdvice.class);

    /**
     * Handles {@link TradeAggregationInfoException} by logging the error and returning the exception message.
     *
     * @param ex the {@link TradeAggregationInfoException} to handle
     * @return the exception message
     */
    @ExceptionHandler(TradeAggregationInfoException.class)
    public String handleTradeAggregationInfoException(TradeAggregationInfoException ex) {
        logger.info("Handling TradeAggregationInfoException");
        logger.debug("Exception details: ", ex);
        // TODO: Complete logic for Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link TradeSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link TradeSaveException} to handle
     * @return the exception message or the cause message if it is a {@link TradeIncoherenceBetweenObject}
     */
    @ExceptionHandler(TradeSaveException.class)
    public String handleTradeSaveException(TradeSaveException ex) {
        logger.info("Handling TradeSaveException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof TradeIncoherenceBetweenObject) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link TradeFindByIdException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link TradeFindByIdException} to handle
     * @return the exception message or the cause message if it is a {@link TradeNotFoundException}
     */
    @ExceptionHandler(TradeFindByIdException.class)
    public String handleTradeFindByIdException(TradeFindByIdException ex) {
        logger.info("Handling TradeFindByIdException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof TradeNotFoundException) {
            String message = ex.getCause().getMessage();
            logger.info("Returning cause message: {}", message);
            return message;
        }
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }

    /**
     * Handles {@link TradeDeleteException} by logging the error and returning the exception message.
     *
     * @param ex the {@link TradeDeleteException} to handle
     * @return the exception message
     */
    @ExceptionHandler(TradeDeleteException.class)
    public String handleTradeDeleteException(TradeDeleteException ex) {
        logger.info("Handling TradeDeleteException");
        logger.debug("Exception details: ", ex);
        // TODO: Complete logic for Controller Advice response and adapt template to manage it
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
    }
}
