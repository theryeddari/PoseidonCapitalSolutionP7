package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RatingTests {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void testRatingCreation() {
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber((byte) 10);

        rating = ratingRepository.save(rating);
        assertNotNull(rating.getId());
        assertEquals(10, (byte) rating.getOrderNumber());
    }

    @Test
    public void testRatingUpdate() {
        // Create and save a rating
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber((byte) 10);
        rating = ratingRepository.save(rating);

        // Update the rating
        rating.setOrderNumber((byte) 20);
        rating = ratingRepository.save(rating);
        assertEquals(20, (byte) rating.getOrderNumber());
    }

    @Test
    public void testFindAllRatings() {
        List<Rating> listResult = ratingRepository.findAll();
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void testDeleteRating() {
        // Create and save a rating
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber((byte) 10);
        rating = ratingRepository.save(rating);

        // Delete the rating
        Integer id = Integer.valueOf(rating.getId());
        ratingRepository.delete(rating);
        Optional<Rating> ratingList = ratingRepository.findById(id);
        assertFalse(ratingList.isPresent());
    }
}
