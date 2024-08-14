package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingResponseAggregationInfoDTO;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        RatingsResponse ratingResponse = ratingService.ratingAggregationInfo();

        List<RatingResponseAggregationInfoDTO> ratingResponseDTOs = ratingResponse.getRatingResponseAggregationInfoDTO();

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
    void testRatingSave() throws RatingSaveException {
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenReturn(rating);

        ratingService.ratingSave(rating);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingSaveException() {
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenThrow(new RuntimeException());

        assertThrows(RatingSaveException.class, () -> ratingService.ratingSave(rating));
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

        Exception exception = Assertions.assertThrows(RatingFindByIdException.class, () -> ratingService.ratingFindById(1));
        assertEquals(RatingNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testRatingUpdateOverloadWithIdVerification_Success() throws RatingUpdateException {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        when(ratingRepository.save(rating)).thenReturn(rating);

        ratingService.ratingUpdate(1, rating);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingUpdateOverloadWithIdVerification_Failed() {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        Exception exception = assertThrows(RatingUpdateException.class, () -> ratingService.ratingUpdate(2, rating));

        assertEquals(RatingIncoherenceBetweenObjectException.class, exception.getCause().getClass());

        verify(ratingRepository, never()).save(rating);
    }

    @Test
    void testRatingUpdateException() {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        when(ratingRepository.save(rating)).thenThrow(new RuntimeException());

        assertThrows(RatingUpdateException.class, () -> ratingService.ratingUpdate(1, rating));
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
