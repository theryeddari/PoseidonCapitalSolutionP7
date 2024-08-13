package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to bid lists.
 */
@AllArgsConstructor
@Getter
public class BidListsResponse {

    List<BidListsResponseAggregationInfoDTO> BidListsResponseAggregationInfoDTO;

    /**
     * Default constructor for BidListsResponse.
     */
    public BidListsResponse() {
        // Lombok constructor
    }
}
