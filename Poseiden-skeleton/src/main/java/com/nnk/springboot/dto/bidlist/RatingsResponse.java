package com.nnk.springboot.dto.bidlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to rating.
 */
@AllArgsConstructor
@Getter
public class RatingsResponse {

    List<RatingResponseAggregationInfoDTO> RatingResponseResponseAggregationInfoDTO;

    /**
     * Default constructor for RatingsResponse.
     * Logs the creation of an instance of RatingsResponse.
     */
    public RatingsResponse() {
        // Lombok constructor
    }
}
