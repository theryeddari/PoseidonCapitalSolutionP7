package com.nnk.springboot.exceptions;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the CurvePoint service.
 */
public class CurvePointServiceException extends Exception {

    /**
     * Constructs a new CurvePointServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public CurvePointServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new CurvePointServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public CurvePointServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating CurvePoint information.
     */
    public static class CurvePointAggregationInfoException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public CurvePointAggregationInfoException(Exception cause) {
            super(CURVE_POINT_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a CurvePoint.
     */
    public static class CurvePointSaveException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public CurvePointSaveException(Exception cause) {
            super(CURVE_POINT_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }

    /**
     * Exception thrown when a CurvePoint cannot be found by ID.
     */
    public static class CurvePointFindByIdException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public CurvePointFindByIdException(Exception cause) {
            super(CURVE_POINT_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }

    /**
     * Exception thrown when a CurvePoint is not found.
     */
    public static class CurvePointNotFoundException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointNotFoundException.
         */
        public CurvePointNotFoundException() {
            super(CURVE_POINT_NOT_FOUND_EXCEPTION);

        }
    }

    /**
     * Exception thrown when there is an incoherence between CurvePoint objects.
     */
    public static class CurvePointIncoherenceBetweenObjectException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointIncoherenceBetweenObjectException.
         */
        public CurvePointIncoherenceBetweenObjectException() {
            super(CURVE_POINT_INCOHERENCE_BETWEEN_OBJET_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a CurvePoint.
     */
    public static class CurvePointDeleteException extends CurvePointServiceException {

        /**
         * Constructs a new CurvePointDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public CurvePointDeleteException(Exception cause) {
            super(CURVE_POINT_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    public static class CurvePointUpdateException extends CurvePointServiceException {
        public CurvePointUpdateException(Exception cause) {
            super(CURVE_POINT_UPDATE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }
}
