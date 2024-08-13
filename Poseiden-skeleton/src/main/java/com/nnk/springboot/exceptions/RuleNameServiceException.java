package com.nnk.springboot.exceptions;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the RuleName service.
 */
public class RuleNameServiceException extends Exception {

    /**
     * Constructs a new RuleNameServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public RuleNameServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new RuleNameServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public RuleNameServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating RuleName information.
     */
    public static class RuleNameAggregationInfoException extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RuleNameAggregationInfoException(Exception cause) {
            super(RULE_NAME_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a RuleName.
     */
    public static class RuleNameSaveException extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RuleNameSaveException(Exception cause) {
            super(RULE_NAME_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a RuleName cannot be found by ID.
     */
    public static class RuleNameFindByIdException extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RuleNameFindByIdException(Exception cause) {
            super(RULE_NAME_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a RuleName is not found.
     */
    public static class RuleNameNotFoundException extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameNotFoundException.
         */
        public RuleNameNotFoundException() {
            super(RULE_NAME_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an incoherence between RuleName objects.
     */
    public static class RuleNameIncoherenceBetweenObject extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameIncoherenceBetweenObject.
         */
        public RuleNameIncoherenceBetweenObject() {
            super(RULE_NAME_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a RuleName.
     */
    public static class RuleNameDeleteException extends RuleNameServiceException {

        /**
         * Constructs a new RuleNameDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public RuleNameDeleteException(Exception cause) {
            super(RULE_NAME_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
}