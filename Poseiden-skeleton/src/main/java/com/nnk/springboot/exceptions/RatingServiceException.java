package com.nnk.springboot.exceptions;
import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the Rating service.
 */
public class RatingServiceException extends Exception {

    /**
     * Constructs a new RatingServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public RatingServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new RatingServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public RatingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating Rating information.
     */
    public static class RatingAggregationInfoException extends RatingServiceException {

        /**
         * Constructs a new RatingAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RatingAggregationInfoException(Exception cause) {
            super(RATING_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a Rating.
     */
    public static class RatingSaveException extends RatingServiceException {

        /**
         * Constructs a new RatingSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RatingSaveException(Exception cause) {
            super(RATING_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a Rating cannot be found by ID.
     */
    public static class RatingFindByIdException extends RatingServiceException {

        /**
         * Constructs a new RatingFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RatingFindByIdException(Exception cause) {
            super(RATING_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a Rating is not found.
     */
    public static class RatingNotFoundException extends RatingServiceException {

        /**
         * Constructs a new RatingNotFoundException.
         */
        public RatingNotFoundException() {
            super(RATING_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an incoherence between Rating objects.
     */
    public static class RatingIncoherenceBetweenObjectException extends RatingServiceException {

        /**
         * Constructs a new RatingIncoherenceBetweenObjectException.
         */
        public RatingIncoherenceBetweenObjectException() {
            super(RATING_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a Rating.
     */
    public static class RatingDeleteException extends RatingServiceException {

        /**
         * Constructs a new RatingDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RatingDeleteException(Exception cause) {
            super(RATING_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error update a Rating.
     */
    public static class RatingUpdateException extends RatingServiceException {

        /**
         * Constructs a new RatingUpdateException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RatingUpdateException(Exception cause) {
            super(RATING_UPDATE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }
}