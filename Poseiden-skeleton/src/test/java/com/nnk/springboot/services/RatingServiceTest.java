package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.bidlist.RatingResponseAggregationInfoDTO;
import com.nnk.springboot.dto.bidlist.RatingsResponse;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.RatingServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @InjectMocks
    RatingService ratingService;

    @Mock
    RatingRepository ratingRepository;

    @Test
    void testRatingAggregationInfo() throws RatingAggregationInfoException {
        Rating rating = new Rating();
        rating.setId((byte) 1);
        rating.setMoodysRating("A");
        rating.setSandPRating("A");
        rating.setFitchRating("A");
        rating.setOrderNumber((byte) 1);

        List<Rating> ratings = List.of(rating);
        when(ratingRepository.findAll()).thenReturn(ratings);

        RatingsResponse ratingsResponse = ratingService.ratingAggregationInfo();

        List<RatingResponseAggregationInfoDTO> ratingResponseDTOs = ratingsResponse.getRatingResponseResponseAggregationInfoDTO();

        assertEquals(String.valueOf(rating.getId()), ratingResponseDTOs.getFirst().getId());
        assertEquals(rating.getMoodysRating(), ratingResponseDTOs.getFirst().getMoodysRating());
        assertEquals(rating.getSandPRating(), ratingResponseDTOs.getFirst().getSandPRating());
        assertEquals(rating.getFitchRating(), ratingResponseDTOs.getFirst().getFitchRating());
        assertEquals(String.valueOf(rating.getOrderNumber()), ratingResponseDTOs.getFirst().getOrderNumber());
    }

    @Test
    void testRatingAggregationInfo_Failed() {
        when(ratingRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RatingAggregationInfoException.class, () -> ratingService.ratingAggregationInfo());
    }

    @Test
    void testRatingSave_BindingSuccess() throws RatingSaveException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");
        when(ratingRepository.save(rating)).thenReturn(rating);

        ratingService.ratingSave(rating, bindingResult);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingSave_BindingError() throws RatingSaveException {
        Rating rating = new Rating();
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        bindingResult.rejectValue("moodysRating", "error.rating", "Moodys Rating is required");

        ratingService.ratingSave(rating, bindingResult);

        verify(ratingRepository, never()).save(rating);
    }

    @Test
    void testRatingFindById() throws RatingFindByIdException {
        Rating rating = new Rating();
        rating.setId((byte) 1);
        rating.setMoodysRating("A");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating response = ratingService.ratingFindById(1);

        assertEquals(1, (byte) response.getId());
        assertEquals("A", response.getMoodysRating());
    }

    @Test
    void testRatingFindById_RatingNotFoundException() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RatingFindByIdException.class, () -> ratingService.ratingFindById(1));
    }

    @Test
    void testRatingSaveOverloadWithIdVerification_Success() throws RatingSaveException {
        Rating rating = new Rating();
        rating.setId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        when(ratingRepository.save(rating)).thenReturn(rating);

        ratingService.ratingSave(1, rating, bindingResult);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingSaveOverloadWithIdVerification_Failed() {
        Rating rating = new Rating();
        rating.setId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        assertThrows(RatingSaveException.class, () -> ratingService.ratingSave(2, rating, bindingResult));

        verify(ratingRepository, never()).save(rating);
    }

    @Test
    void testRatingSaveException() {
        Rating rating = new Rating();
        rating.setId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(rating, "rating");

        when(ratingRepository.save(rating)).thenThrow(new RuntimeException());

        assertThrows(RatingSaveException.class, () -> ratingService.ratingSave(1, rating, bindingResult));
    }

    @Test
    void testRatingDelete() throws RatingDeleteException {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.ratingDelete(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }

    @Test
    void testRatingDeleteException() {
        doThrow(new RuntimeException()).when(ratingRepository).deleteById(anyInt());

        assertThrows(RatingDeleteException.class, () -> ratingService.ratingDelete(1));
    }
}
