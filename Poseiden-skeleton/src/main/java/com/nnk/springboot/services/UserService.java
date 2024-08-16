package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserResponse;
import com.nnk.springboot.dto.UserResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.UserServiceException;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.UserServiceException.*;

/**
 * Class that manages logic about operations of User.
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.encoder = bCryptPasswordEncoder;
    }

    /**
     * Retrieves aggregated user information.
     *
     * @return UserResponse containing aggregated user information.
     * @throws UserAggregationInfoException if there is an error retrieving the information.
     */
    public UserResponse userAggregationInfo() throws UserAggregationInfoException {
        logger.info("Entering userAggregationInfo method.");
        try {
            List<User> users = userRepository.findAll();
            List<UserResponseAggregationInfoDTO> userResponseAggregationInfoDTO = users.stream()
                    .map(user -> new UserResponseAggregationInfoDTO(
                            String.valueOf(user.getId()),
                            user.getUsername(),
                            user.getFullname(),
                            user.getRole()))
                    .toList();
            logger.info("Exiting userAggregationInfo method successfully.");
            return new UserResponse(userResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in userAggregationInfo method.", e);
            throw new UserAggregationInfoException(e);
        }
    }

    /**
     * Saves a user entity.
     *
     * @param user the user to save.
     * @return the saved user.
     * @throws UserSaveException if there is an error saving the user.
     */
    public User userSave(User user) throws UserSaveException {
        logger.info("Entering userSave method with user: {}", user);
        try {
            if(user.getId() == null && (userRepository.existsByUsername(user.getUsername()))) { throw new UserServiceException.UsernameAlreadyExistException(); }
            user.setPassword(encoder.encode(user.getPassword()));
            user = userRepository.save(user);
            logger.info("Exiting userSave method successfully with saved user: {}", user);
            return user;
        } catch (Exception e) {
            logger.error("Error in userSave method.", e);
            throw new UserSaveException(e);
        }
    }

    /**
     * Finds a user by its ID.
     *
     * @param id the ID of the user to find.
     * @return the found user.
     * @throws UserFindByIdException if there is an error finding the user.
     */
    public User userFindById(int id) throws UserFindByIdException {
        logger.info("Entering userFindById method with ID: {}", id);
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                logger.warn("User with ID: {} not found.", id);
                throw new UserNotFoundException();
            }
            logger.info("Exiting userFindById method successfully with found user: {}", user.get());
            return user.get();
        } catch (Exception e) {
            logger.error("Error in userFindById method.", e);
            throw new UserFindByIdException(e);
        }
    }

    /**
     * Updates a user entity with a given ID.
     *
     * @param id the ID to validate.
     * @param user the user to update.
     * @throws UserUpdateException if there is an error updating the user.
     */
    public void userUpdate(int id, User user) throws UserUpdateException {
        logger.info("Entering userUpdate method with ID: {} and user: {}", id, user);
        try {
            if (id == user.getId()) {
                User updatedUser = userSave(user);
                logger.info("Exiting userUpdate method successfully with updated user: {}", updatedUser);
            } else {
                logger.warn("User ID: {} does not match the ID in user: {}", id, user.getId());
                throw new UserIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in userUpdate method.", e);
            throw new UserUpdateException(e);
        }
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete.
     * @throws UserDeleteException if there is an error deleting the user.
     */
    public void userDelete(int id) throws UserDeleteException {
        logger.info("Entering userDelete method with ID: {}", id);
        try {
            userRepository.deleteById(id);
            logger.info("Exiting userDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in userDelete method.", e);
            throw new UserDeleteException(e);
        }
    }
}
