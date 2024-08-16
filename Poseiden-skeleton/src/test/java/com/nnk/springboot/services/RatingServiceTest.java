package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingResponseAggregationInfoDTO;
import com.nnk.springboot.dto.RatingsResponse;
import com.nnk.springboot.exceptions.RatingServiceException.*;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

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

        RatingsResponse response = ratingService.ratingAggregationInfo();

        List<RatingResponseAggregationInfoDTO> dtos = response.getRatingResponseAggregationInfoDTO();

        assertEquals("1", dtos.getFirst().getId());
        assertEquals("A", dtos.getFirst().getMoodysRating());
        assertEquals("A", dtos.getFirst().getSandPRating());
        assertEquals("A", dtos.getFirst().getFitchRating());
        assertEquals("1", dtos.getFirst().getOrderNumber());
    }

    @Test
    void testRatingAggregationInfo_Failed() {
        when(ratingRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RatingAggregationInfoException.class, () -> ratingService.ratingAggregationInfo());
    }

    @Test
    void testRatingSave_Success() throws RatingSaveException {
        Rating rating = new Rating();

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating savedRating = ratingService.ratingSave(rating);

        assertEquals(rating, savedRating);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingSave_Exception() {
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

        assertEquals((byte) 1, response.getId());
        assertEquals("A", response.getMoodysRating());
    }

    @Test
    void testRatingFindById_Exception() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RatingFindByIdException.class, () -> ratingService.ratingFindById(1));
    }

    @Test
    void testRatingUpdate_Success() throws RatingUpdateException {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        when(ratingRepository.save(rating)).thenReturn(rating);
        ratingService.ratingUpdate(1, rating);

        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testRatingUpdate_IDMismatch() {
        Rating rating = new Rating();
        rating.setId((byte) 1);

        Exception exception = assertThrows(RatingUpdateException.class, () -> ratingService.ratingUpdate(2, rating));
        assertEquals(RatingIncoherenceBetweenObjectException.class, exception.getCause().getClass());
    }

    @Test
    void testRatingUpdate_Exception() {
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
    void testRatingDelete_Exception() {
        doThrow(new RuntimeException()).when(ratingRepository).deleteById(1);

        assertThrows(RatingDeleteException.class, () -> ratingService.ratingDelete(1));
    }
}
