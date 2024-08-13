package com.nnk.springboot.exceptions;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the Trade service.
 */
public class TradeServiceException extends Exception {

    /**
     * Constructs a new TradeServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public TradeServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new TradeServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public TradeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating Trade information.
     */
    public static class TradeAggregationInfoException extends TradeServiceException {

        /**
         * Constructs a new TradeAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public TradeAggregationInfoException(Exception cause) {
            super(TRADE_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a Trade.
     */
    public static class TradeSaveException extends TradeServiceException {

        /**
         * Constructs a new TradeSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public TradeSaveException(Exception cause) {
            super(TRADE_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a Trade cannot be found by ID.
     */
    public static class TradeFindByIdException extends TradeServiceException {

        /**
         * Constructs a new TradeFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public TradeFindByIdException(Exception cause) {
            super(TRADE_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a Trade is not found.
     */
    public static class TradeNotFoundException extends TradeServiceException {

        /**
         * Constructs a new TradeNotFoundException.
         */
        public TradeNotFoundException() {
            super(TRADE_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an incoherence between Trade objects.
     */
    public static class TradeIncoherenceBetweenObject extends TradeServiceException {

        /**
         * Constructs a new TradeIncoherenceBetweenObject.
         */
        public TradeIncoherenceBetweenObject() {
            super(TRADE_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a Trade.
     */
    public static class TradeDeleteException extends TradeServiceException {

        /**
         * Constructs a new TradeDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public TradeDeleteException(Exception cause) {
            super(TRADE_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
}
