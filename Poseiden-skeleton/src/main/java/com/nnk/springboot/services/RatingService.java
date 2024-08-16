package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingResponseAggregationInfoDTO;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.exceptions.RatingServiceException;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class that manages logic about operations of Rating.
 */
@Service
@Transactional
public class RatingService {

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Retrieves aggregated rating information.
     *
     * @return RatingsResponse containing aggregated rating information.
     * @throws RatingServiceException.RatingAggregationInfoException if there is an error retrieving the information.
     */
    public RatingsResponse ratingAggregationInfo() throws RatingServiceException.RatingAggregationInfoException {
        logger.info("Entering ratingAggregationInfo method.");
        try {
            List<Rating> ratings = ratingRepository.findAll();
            List<RatingResponseAggregationInfoDTO> ratingResponseAggregationInfoDTO = ratings.stream()
                    .map(rating -> new RatingResponseAggregationInfoDTO(
                            String.valueOf(rating.getId()),
                            rating.getMoodysRating(),
                            rating.getSandPRating(),
                            rating.getFitchRating(),
                            String.valueOf(rating.getOrderNumber())))
                    .toList();
            logger.info("Exiting ratingAggregationInfo method successfully.");
            return new RatingsResponse(ratingResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in ratingAggregationInfo method.", e);
            throw new RatingServiceException.RatingAggregationInfoException(e);
        }
    }

    /**
     * Saves a rating entity.
     *
     * @param rating the rating to save.
     * @return the saved rating.
     * @throws RatingServiceException.RatingSaveException if there is an error saving the rating.
     */
    public Rating ratingSave(Rating rating) throws RatingServiceException.RatingSaveException {
        logger.info("Entering ratingSave method with rating: {}", rating);
        try {
            rating = ratingRepository.save(rating);
            logger.info("Exiting ratingSave method successfully with saved rating: {}", rating);
            return rating;
        } catch (Exception e) {
            logger.error("Error in ratingSave method.", e);
            throw new RatingServiceException.RatingSaveException(e);
        }
    }

    /**
     * Finds a rating by its ID.
     *
     * @param id the ID of the rating to find.
     * @return the found rating.
     * @throws RatingServiceException.RatingFindByIdException if there is an error finding the rating.
     */
    public Rating ratingFindById(int id) throws RatingServiceException.RatingFindByIdException {
        logger.info("Entering ratingFindById method with ID: {}", id);
        try {
            Optional<Rating> rating = ratingRepository.findById(id);
            if (rating.isEmpty()) {
                logger.warn("Rating with ID: {} not found.", id);
                throw new RatingServiceException.RatingNotFoundException();
            }
            logger.info("Exiting ratingFindById method successfully with found rating: {}", rating.get());
            return rating.get();
        } catch (Exception e) {
            logger.error("Error in ratingFindById method.", e);
            throw new RatingServiceException.RatingFindByIdException(e);
        }
    }

    /**
     * Updates a rating entity with a given ID.
     *
     * @param id the ID to validate.
     * @param rating the rating to update.
     * @throws RatingServiceException.RatingUpdateException if there is an error updating the rating.
     */
    public void ratingUpdate(int id, Rating rating) throws RatingServiceException.RatingUpdateException {
        logger.info("Entering ratingUpdate method with ID: {} and rating: {}", id, rating);
        try {
            if (id == rating.getId()) {
                Rating updatedRating = ratingSave(rating);
                logger.info("Exiting ratingUpdate method successfully with updated rating: {}", updatedRating);
            } else {
                logger.warn("Rating ID: {} does not match the ID in rating: {}", id, rating.getId());
                throw new RatingServiceException.RatingIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in ratingUpdate method.", e);
            throw new RatingServiceException.RatingUpdateException(e);
        }
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param id the ID of the rating to delete.
     * @throws RatingServiceException.RatingDeleteException if there is an error deleting the rating.
     */
    public void ratingDelete(int id) throws RatingServiceException.RatingDeleteException {
        logger.info("Entering ratingDelete method with ID: {}", id);
        try {
            ratingRepository.deleteById(id);
            logger.info("Exiting ratingDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in ratingDelete method.", e);
            throw new RatingServiceException.RatingDeleteException(e);
        }
    }
}
