package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to rating.
 */
@AllArgsConstructor
@Getter
public class RatingsResponse {

    List<RatingResponseAggregationInfoDTO> RatingResponseAggregationInfoDTO;

    /**
     * Default constructor for RatingsResponse.
     */
    public RatingsResponse() {
        // Lombok constructor
    }
}
