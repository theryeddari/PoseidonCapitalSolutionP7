package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingResponseAggregationInfoDTO;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.RatingServiceException.*;

/**
 * Class that manages logic about operations of Rating.
 */
@Service
@Transactional
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Retrieves aggregated rating information.
     *
     * @return RatingsResponse containing aggregated rating information.
     * @throws RatingAggregationInfoException if there is an error retrieving the information.
     */
    public RatingsResponse ratingAggregationInfo() throws RatingAggregationInfoException {
        logger.info("Entering ratingAggregationInfo method.");
        try {
            List<Rating> ratings = ratingRepository.findAll();
            List<RatingResponseAggregationInfoDTO> ratingResponseAggregationInfoDTO = ratings.stream().map(rating ->
                            new RatingResponseAggregationInfoDTO(
                                    String.valueOf(rating.getId()),
                                    rating.getMoodysRating(),
                                    rating.getSandPRating(),
                                    rating.getFitchRating(),
                                    String.valueOf(rating.getOrderNumber())
                                    ))
                    .toList();
            logger.info("Exiting ratingAggregationInfo method successfully.");
            return new RatingsResponse(ratingResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in ratingAggregationInfo method.", e);
            throw new RatingAggregationInfoException(e);
        }
    }

    /**
     * Saves a rating entity.
     *
     * @param rating         the rating to save.
     * @param bindingResult  the result of binding validation.
     * @return the saved rating.
     * @throws RatingSaveException if there is an error saving the rating.
     */
    public Rating ratingSave(Rating rating, BindingResult bindingResult) throws RatingSaveException {
        logger.info("Entering ratingSave method with rating: {}", rating);
        try {
            if (!bindingResult.hasFieldErrors()) {
                rating = ratingRepository.save(rating);
            }
            logger.info("Exiting ratingSave method successfully with saved rating: {}", rating);
            return rating;
        } catch (Exception e) {
            logger.error("Error in ratingSave method.", e);
            throw new RatingSaveException(e);
        }
    }

    /**
     * Finds a rating by its ID.
     *
     * @param id the ID of the rating to find.
     * @return the found rating.
     * @throws RatingFindByIdException if there is an error finding the rating.
     */
    public Rating ratingFindById(int id) throws RatingFindByIdException {
        logger.info("Entering ratingFindById method with ID: {}", id);
        try {
            Optional<Rating> rating = ratingRepository.findById(id);
            if (rating.isEmpty()) {
                logger.warn("Rating with ID: {} not found.", id);
                throw new RatingNotFoundException();
            }
            logger.info("Exiting ratingFindById method successfully with found rating: {}", rating.get());
            return rating.get();
        } catch (Exception e) {
            logger.error("Error in ratingFindById method.", e);
            throw new RatingFindByIdException(e);
        }
    }

    /**
     * Saves a rating entity with a given ID.
     *
     * @param id            the ID to validate.
     * @param rating        the rating to save.
     * @param bindingResult the result of binding validation.
     * @return the saved rating.
     * @throws RatingSaveException if there is an error saving the rating.
     */
    public Rating ratingSave(int id, Rating rating, BindingResult bindingResult) throws RatingSaveException {
        logger.info("Entering ratingSave method with ID: {} and rating: {}", id, rating);
        try {
            if (id == rating.getId()) {
                Rating savedRating = ratingSave(rating, bindingResult);
                logger.info("Exiting ratingSave method successfully with saved rating: {}", savedRating);
                return savedRating;
            } else {
                logger.warn("Rating ID: {} does not match the ID in rating: {}", id, rating.getId());
                throw new RatingIncoherenceBetweenObject();
            }
        } catch (Exception e) {
            logger.error("Error in ratingSave method.", e);
            throw new RatingSaveException(e);
        }
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param id the ID of the rating to delete.
     * @throws RatingDeleteException if there is an error deleting the rating.
     */
    public void ratingDelete(int id) throws RatingDeleteException {
        logger.info("Entering ratingDelete method with ID: {}", id);
        try {
            ratingRepository.deleteById(id);
            logger.info("Exiting ratingDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in ratingDelete method.", e);
            throw new RatingDeleteException(e);
        }
    }
}
