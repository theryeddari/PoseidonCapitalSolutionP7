package com.nnk.springboot.exceptions;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

/**
 * Custom exception class for handling exceptions in the User service.
 */
public class UserServiceException extends Exception {

    /**
     * Constructs a new UserServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when there is an error aggregating User information.
     */
    public static class UserAggregationInfoException extends UserServiceException {

        /**
         * Constructs a new UserAggregationInfoException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public UserAggregationInfoException(Exception cause) {
            super(USER_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error saving a User.
     */
    public static class UserSaveException extends UserServiceException {

        /**
         * Constructs a new UserSaveException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public UserSaveException(Exception cause) {
            super(USER_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a User cannot be found by ID.
     */
    public static class UserFindByIdException extends UserServiceException {

        /**
         * Constructs a new UserFindByIdException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public UserFindByIdException(Exception cause) {
            super(USER_FIND_BY_ID_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when a User is not found.
     */
    public static class UserNotFoundException extends UserServiceException {

        /**
         * Constructs a new UserNotFoundException.
         */
        public UserNotFoundException() {
            super(USER_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an incoherence between User objects.
     */
    public static class UserIncoherenceBetweenObjectException extends UserServiceException {

        /**
         * Constructs a new UserIncoherenceBetweenObjectException.
         */
        public UserIncoherenceBetweenObjectException() {
            super(USER_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION);
        }
    }

    /**
     * Exception thrown when there is an error deleting a User.
     */
    public static class UserDeleteException extends UserServiceException {

        /**
         * Constructs a new UserDeleteException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public UserDeleteException(Exception cause) {
            super(USER_DELETE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error updating a User.
     */
    public static class UserUpdateException extends UserServiceException {

        /**
         * Constructs a new UserUpdateException with the specified cause.
         *
         * @param cause the cause of the exception
         */
        public UserUpdateException(Exception cause) {
            super(USER_UPDATE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception thrown when there is an error about username already exist before add a User.
     */

    public static class UsernameAlreadyExistException extends UserServiceException {

        /**
         * Constructs a new UsernameAlreadyExistException.
         *
         */

        public UsernameAlreadyExistException() {
            super(USERNAME_ALREADY_EXIST_EXCEPTION);
        }
    }
}
