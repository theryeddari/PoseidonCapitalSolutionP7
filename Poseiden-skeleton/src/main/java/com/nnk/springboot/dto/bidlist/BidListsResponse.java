package com.nnk.springboot.dto.bidlist;

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
     * Logs the creation of an instance of BidListsResponse.
     */
    public BidListsResponse() {
        // Lombok constructor
    }
}
