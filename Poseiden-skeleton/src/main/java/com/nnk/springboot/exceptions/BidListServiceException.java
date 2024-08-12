package com.nnk.springboot.exceptions;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the BidList service.
 */
public class BidListServiceException extends Exception {

    /**
     * Constructs a new BidListServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public BidListServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new BidListServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public BidListServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating BidList information.
     */
    public static class BidListAggregationInfoException extends BidListServiceException {

        /**
         * Constructs a new BidListAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public BidListAggregationInfoException(Exception cause) {
            super(BID_LIST_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a BidList.
     */
    public static class BidListSaveException extends BidListServiceException {

        /**
         * Constructs a new BidListSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public BidListSaveException(Exception cause) {
            super(BID_LIST_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }

    /**
     * Exception thrown when a BidList cannot be found by ID.
     */
    public static class BidListFindByIdException extends BidListServiceException {

        /**
         * Constructs a new BidListFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public BidListFindByIdException(Exception cause) {
            super(BID_LIST_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }

    /**
     * Exception thrown when a BidList is not found.
     */
    public static class BidListNotFoundException extends BidListServiceException {

        /**
         * Constructs a new BidListNotFoundException.
         */
        public BidListNotFoundException() {
            super(BID_LIST_NOT_FOUND_EXCEPTION);

        }
    }

    /**
     * Exception thrown when there is an incoherence between BidList objects.
     */
    public static class BidListIncoherenceBetweenObject extends BidListServiceException {

        /**
         * Constructs a new BidListIncoherenceBetweenObject.
         */
        public BidListIncoherenceBetweenObject() {
            super(BID_LIST_INCOHERENCE_BETWEEN_OBJET_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a BidList.
     */
    public static class BidListDeleteException extends BidListServiceException {

        /**
         * Constructs a new BidListDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public BidListDeleteException(Exception cause) {
            super(BID_LIST_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
}
