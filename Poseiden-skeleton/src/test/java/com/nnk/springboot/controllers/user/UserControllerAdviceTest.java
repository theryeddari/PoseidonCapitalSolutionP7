package com.nnk.springboot.controllers.user;

import com.nnk.springboot.controllers.UserControllerAdvice;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserServiceException.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Objects;

import static com.nnk.springboot.constants.ConstantsExceptions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerAdviceTest {

    @Mock
    private Model model;

    UserControllerAdvice userControllerAdvice = new UserControllerAdvice();

    @Test
    void handleUserAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = userControllerAdvice.handleUserAggregationInfoException(new UserAggregationInfoException(runtimeException));

        assertTrue(result.contains(USER_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserSaveException_UserAlreadyExists() {
        UsernameAlreadyExistException causeException = new UsernameAlreadyExistException();
        UserSaveException exception = new UserSaveException(causeException);


        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
        bindingResult.rejectValue("username", "error.user", exception.getCause().getMessage());


        // Mock interactions with Model
        when(model.getAttribute("org.springframework.validation.BindingResult.user")).thenReturn(bindingResult);

        String viewName = userControllerAdvice.handleUserSaveException(exception, model);

        verify(model).addAttribute(eq("org.springframework.validation.BindingResult.user"), any(BindingResult.class));
        verify(model).addAttribute(eq("user"), any(User.class));
        assertEquals("user/add", viewName);

        // Additional assertions to verify that the error message is set
        BindingResult result = (BindingResult) model.getAttribute("org.springframework.validation.BindingResult.user");
        assert result != null;
        assertTrue(result.hasErrors());
        assertEquals(USERNAME_ALREADY_EXIST_EXCEPTION, Objects.requireNonNull(result.getFieldError("username")).getDefaultMessage());
    }

    @Test
    void handleUserSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = userControllerAdvice.handleUserSaveException(new UserSaveException(runtimeException), model);

        Assertions.assertTrue(result.contains(USER_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserFindByIdException() {
        String result = userControllerAdvice.handleUserFindByIdException(new UserFindByIdException(new RuntimeException()));

        assertTrue(result.contains(USER_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleUserFindByIdException_UserNotFoundException() {
        UserNotFoundException userNotFoundException = new UserNotFoundException();
        String result = userControllerAdvice.handleUserFindByIdException(new UserFindByIdException(userNotFoundException));

        assertTrue(result.contains(USER_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleUserUpdateException() { // Modification ici pour correspondre à la classe modèle
        String result = userControllerAdvice.handleUserUpdateException(new UserUpdateException(new RuntimeException()));

        assertTrue(result.contains(USER_UPDATE_EXCEPTION));
    }

    @Test
    void handleUserUpdateException_WithIdVerification_Failed() { // Modification ici pour correspondre à la classe modèle
        UserIncoherenceBetweenObjectException userIncoherenceBetweenObjectException = new UserIncoherenceBetweenObjectException();
        String result = userControllerAdvice.handleUserUpdateException(new UserUpdateException(userIncoherenceBetweenObjectException));

        assertTrue(result.contains(USER_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION));
    }

    @Test
    void handleUserDeleteException() {
        UserDeleteException userDeleteException = new UserDeleteException(new RuntimeException());
        String result = userControllerAdvice.handleUserDeleteException(userDeleteException);

        assertTrue(result.contains(USER_DELETE_EXCEPTION + MORE_INFO));
    }
}
