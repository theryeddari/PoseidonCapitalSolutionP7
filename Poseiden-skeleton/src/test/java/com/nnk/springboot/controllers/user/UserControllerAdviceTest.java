package com.nnk.springboot.controllers.user;

import com.nnk.springboot.controllers.UserControllerAdvice;
import com.nnk.springboot.exceptions.UserServiceException.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerAdviceTest {

    UserControllerAdvice userControllerAdvice = new UserControllerAdvice();

    @Test
    void handleUserAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = userControllerAdvice.handleUserAggregationInfoException(new UserAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(USER_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = userControllerAdvice.handleUserSaveException(new UserSaveException(runtimeException));

        Assertions.assertTrue(result.contains(USER_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserFindByIdException() {
        String result = userControllerAdvice.handleUserFindByIdException(new UserFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(USER_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserFindByIdException_UserNotFoundException() {
        UserNotFoundException userNotFoundException = new UserNotFoundException();
        String result = userControllerAdvice.handleUserFindByIdException(new UserFindByIdException(userNotFoundException));

        Assertions.assertTrue(result.contains(USER_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleUserUpdateException() { // Modification ici pour correspondre à la classe modèle
        String result = userControllerAdvice.handleUserUpdateException(new UserUpdateException(new RuntimeException()));

        Assertions.assertTrue(result.contains(USER_UPDATE_EXCEPTION));
    }

    @Test
    void handleUserUpdateException_WithIdVerification_Failed() { // Modification ici pour correspondre à la classe modèle
        UserIncoherenceBetweenObjectException userIncoherenceBetweenObjectException = new UserIncoherenceBetweenObjectException();
        String result = userControllerAdvice.handleUserUpdateException(new UserUpdateException(userIncoherenceBetweenObjectException));

        Assertions.assertTrue(result.contains(USER_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION));
    }

    @Test
    void handleUserDeleteException() {
        UserDeleteException userDeleteException = new UserDeleteException(new RuntimeException());
        String result = userControllerAdvice.handleUserDeleteException(userDeleteException);

        Assertions.assertTrue(result.contains(USER_DELETE_EXCEPTION + MORE_INFO));
    }
}
