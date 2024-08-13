package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for aggregating rating response information.
 * <p>
 * This DTO is used to transfer rating information between layers of the application.
 * It includes details about the rating such as id, moodysRating, sandPRating, fitchRating, and order.
 * </p>
 */
@Getter
@AllArgsConstructor
public class RatingResponseAggregationInfoDTO {

    /**
     * The unique identifier for the rating.
     */
    private String id;

    /**
     * The Moody's rating associated with the entity.
     */
    private String moodysRating;

    /**
     * The S&P rating associated with the entity.
     */
    private String sandPRating;

    /**
     * The Fitch rating associated with the entity.
     */
    private String fitchRating;

    /**
     * The order of the rating in the context of other ratings.
     */
    private String orderNumber;
}
