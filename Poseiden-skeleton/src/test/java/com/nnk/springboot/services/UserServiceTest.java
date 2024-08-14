package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserResponse;
import com.nnk.springboot.dto.UserResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.UserServiceException.*;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void testUserAggregationInfo() throws UserAggregationInfoException {
        User user = new User();
        user.setId((byte) 1);
        user.setUsername("user");
        user.setFullname("Full Name");
        user.setRole("Role");

        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        UserResponse userResponse = userService.userAggregationInfo();

        List<UserResponseAggregationInfoDTO> userResponseAggregationInfoDTO = userResponse.getUserResponseAggregationInfoDTO();

        assertEquals(String.valueOf(user.getId()), userResponseAggregationInfoDTO.getFirst().getId());
        assertEquals(user.getUsername(), userResponseAggregationInfoDTO.getFirst().getUsername());
        assertEquals(user.getFullname(), userResponseAggregationInfoDTO.getFirst().getFullname());
        assertEquals(user.getRole(), userResponseAggregationInfoDTO.getFirst().getRole());
    }

    @Test
    void testUserAggregationInfo_Failed() {
        when(userRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(UserAggregationInfoException.class, () -> userService.userAggregationInfo());
    }

    @Test
    void testGetUserById() throws UserFindByIdException {
        User user = new User();
        user.setId((byte) 1);
        user.setUsername("user");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User response = userService.getUserById(1);

        assertEquals((byte) 1, response.getId());
        assertEquals("user", response.getUsername());
    }

    @Test
    void testGetUserById_UserNotFoundException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(UserFindByIdException.class, () -> userService.getUserById(1));
        assertEquals(UserNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testUserSave_Success() throws UserSaveException {
        User user = new User();
        user.setPassword("password");

        when(encoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.userSave(user);

        assertEquals(user, savedUser);
        verify(userRepository, times(1)).save(user);
        verify(encoder, times(1)).encode("password");
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    void testUserSave_Exception() {
        User user = new User();
        user.setPassword("password");
        when(encoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenThrow(new RuntimeException());

        assertThrows(UserSaveException.class, () -> userService.userSave(user));
    }

    @Test
    void testUserUpdate_Success() throws UserSaveException {
        User user = new User();
        user.setId((byte) 1);

        when(userRepository.save(user)).thenReturn(user);
        userService.userUpdate(1, user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUserUpdate_IDMismatch() {
        User user = new User();
        user.setId((byte) 1);

        Exception exception = assertThrows(UserSaveException.class, () -> userService.userUpdate(2, user));
        assertEquals(UserIncoherenceBetweenObjectException.class, exception.getCause().getClass());
    }

    @Test
    void testUserUpdate_Exception() {
        User user = new User();
        user.setId((byte) 1);

        when(userRepository.save(user)).thenThrow(new RuntimeException());

        assertThrows(UserSaveException.class, () -> userService.userUpdate(1, user));
    }

    @Test
    void testDeleteUser() throws UserDeleteException {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_Exception() {
        doThrow(new RuntimeException()).when(userRepository).deleteById(1);

        assertThrows(UserDeleteException.class, () -> userService.deleteUser(1));
    }
}
