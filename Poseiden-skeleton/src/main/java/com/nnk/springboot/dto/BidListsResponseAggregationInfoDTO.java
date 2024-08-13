package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for aggregating bid list response information.
 * <p>
 * This DTO is used to transfer bid list information between layers of the application.
 * It includes details about the bid list such as id, account, type, and bid quantity.
 * </p>
 */
@Getter
@AllArgsConstructor
public class BidListsResponseAggregationInfoDTO {

    /**
     * The unique identifier for the bid list.
     */
    private String id;

    /**
     * The account associated with the bid list.
     */
    private String account;

    /**
     * The type of the bid list.
     */
    private String type;

    /**
     * The quantity of the bid in the bid list.
     */
    private String bidQuantity;

    /**
     * Default constructor for BidListsResponseAggregationInfoDTO.
     * <p>
     * This constructor is provided by Lombok and initializes a new instance of
     * BidListsResponseAggregationInfoDTO with default values.
     * </p>
     */
    public BidListsResponseAggregationInfoDTO() {
        // Lombok constructor
    }
}
