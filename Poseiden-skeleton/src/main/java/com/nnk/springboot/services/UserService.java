package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserResponse;
import com.nnk.springboot.dto.UserResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.UserServiceException.*;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.encoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
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
            List<UserResponseAggregationInfoDTO> userResponseAggregationInfoDTO = users.stream().map(user ->
                            new UserResponseAggregationInfoDTO(
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
     * @throws UserSaveException if there is an error saving the user.
     */
    public User userSave(User user) throws UserSaveException {
        logger.info("Entering saveUser method with user: {}", user);
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user = userRepository.save(user);
            logger.info("Exiting saveUser method successfully with saved user: {}", user);
            return user;
        } catch (Exception e) {
            logger.error("Error in saveUser method.", e);
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
    public User getUserById(int id) throws UserFindByIdException {
        logger.info("Entering getUserById method with ID: {}", id);
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                logger.warn("User with ID: {} not found.", id);
                throw new UserNotFoundException();
            }
            logger.info("Exiting getUserById method successfully with found user: {}", user.get());
            return user.get();
        } catch (Exception e) {
            logger.error("Error in getUserById method.", e);
            throw new UserFindByIdException(e);
        }
    }

    /**
     * Updates the specified user.
     *
     * @param id the ID to validate.
     * @param user the user data to update.
     * @throws UserSaveException if there is an error saving the user.
     */
    public void userUpdate(int id, User user) throws UserSaveException {
        logger.info("Entering userUpdate method with ID: {} and user: {}", id, user);
        try {
            if (id == user.getId()) {
                User updatedUser = userSave(user);
                logger.info("Exiting userUpdate method successfully with updated user: {}", updatedUser);
            } else {
                // If IDs do not match, throw an exception
                logger.warn("User ID: {} does not match the ID in user: {}", id, user.getId());
                throw new UserIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in userUpdate method.", e);
            throw new UserSaveException(e);
        }
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete.
     * @throws UserDeleteException if there is an error deleting the user.
     */
    public void deleteUser(int id) throws UserDeleteException {
        logger.info("Entering deleteUser method with ID: {}", id);
        try {
            userRepository.deleteById(id);
            logger.info("Exiting deleteUser method successfully.");
        } catch (Exception e) {
            logger.error("Error in deleteUser method.", e);
            throw new UserDeleteException(e);
        }
    }
}
