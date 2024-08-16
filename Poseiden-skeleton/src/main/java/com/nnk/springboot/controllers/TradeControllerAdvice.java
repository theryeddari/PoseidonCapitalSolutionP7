package com.nnk.springboot.controllers;

import com.nnk.springboot.exceptions.TradeServiceException.TradeAggregationInfoException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeSaveException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeUpdateException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeFindByIdException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeDeleteException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeIncoherenceBetweenObjectException;
import com.nnk.springboot.exceptions.TradeServiceException.TradeNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TradeControllerAdvice {

    private static final Logger logger = LogManager.getLogger(TradeControllerAdvice.class);

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
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link TradeSaveException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link TradeSaveException} to handle
     * @return the exception message or the cause message if it is a {@link TradeIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(TradeSaveException.class)
    public String handleTradeSaveException(TradeSaveException ex) {
        logger.info("Handling TradeSaveException");
        logger.debug("Exception details: ", ex);
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }

    /**
     * Handles {@link TradeUpdateException} by logging the error and returning the appropriate message
     * based on the cause of the exception.
     *
     * @param ex the {@link TradeUpdateException} to handle
     * @return the exception message or the cause message if it is a {@link TradeIncoherenceBetweenObjectException}
     */
    @ExceptionHandler(TradeUpdateException.class)
    public String handleTradeUpdateException(TradeUpdateException ex) {
        logger.info("Handling TradeUpdateException");
        logger.debug("Exception details: ", ex);
        if (ex.getCause() instanceof TradeIncoherenceBetweenObjectException) {
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
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

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
        String message = ex.getMessage();
        logger.info("Returning response message: {}", message);
        return message;
        //TODO: complete logic Controller Advice error response for template (future release, del BindingResult todo it(block throw Validation exception))

    }
}
