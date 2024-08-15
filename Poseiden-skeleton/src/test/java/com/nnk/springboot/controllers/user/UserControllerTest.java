package com.nnk.springboot.controllers.user;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserResponse;
import com.nnk.springboot.exceptions.UserServiceException.*;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Test
    void testHome() throws UserAggregationInfoException {
        UserResponse userResponse = new UserResponse();

        when(userService.userAggregationInfo()).thenReturn(userResponse);

        String viewName = userController.home(model);

        verify(userService, times(1)).userAggregationInfo();
        assertEquals("user/list", viewName);
        verify(model, times(1)).addAttribute(eq("users"), eq(userResponse.getUserResponseAggregationInfoDTO()));
    }

    @Test
    void testAddUserForm() {
        User user = new User();

        String viewName = userController.addUserForm(user, model);

        assertEquals("user/add", viewName);
        verify(model, times(1)).addAttribute(eq("user"), eq(user));
    }

    @Test
    void testValidate_True() throws UserSaveException {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        when(userService.userSave(user)).thenReturn(user);

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).userSave(user);
        verify(model, never()).addAttribute(eq("user"), eq(user));
    }

    @Test
    void testValidate_False() throws UserSaveException {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        bindingResult.rejectValue("username", "error.user", "Username is required");

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("user/add", viewName);
        verify(model, times(1)).addAttribute(eq("user"), eq(user));
        verify(userService, never()).userSave(user);
    }

    @Test
    void testShowUpdateForm() throws UserFindByIdException {
        User user = new User();
        user.setId((byte) 1);

        when(userService.userFindById(1)).thenReturn(user);

        String viewName = userController.showUpdateForm(1, model);

        verify(userService, times(1)).userFindById(1);
        assertEquals("user/update", viewName);
        verify(model, times(1)).addAttribute(eq("user"), eq(user));
    }

    @Test
    void testUpdateUser_True() throws UserUpdateException {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        doNothing().when(userService).userUpdate(1, user);

        String viewName = userController.updateUser(1, user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).userUpdate(1, user);
    }

    @Test
    void testUpdateUser_False() throws UserUpdateException {
        User user = new User();
        BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");

        bindingResult.rejectValue("username", "error.user", "Username is required");

        String viewName = userController.updateUser(1, user, bindingResult, model);

        assertEquals("user/update", viewName);
        verify(model, times(1)).addAttribute(eq("user"), eq(user));
        verify(userService, never()).userUpdate(1, user);
    }

    @Test
    void testDeleteUser() throws UserDeleteException {
        doNothing().when(userService).userDelete(1);

        String viewName = userController.deleteUser(1);

        verify(userService, times(1)).userDelete(1);
        assertEquals("redirect:/user/list", viewName);
    }
}
