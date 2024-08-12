package com.nnk.springboot.dto.bidlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to bid lists.
 */
@AllArgsConstructor
@Getter
public class BidListsResponse {

    private static final Logger logger = LoggerFactory.getLogger(BidListsResponse.class);

    List<BidListsResponseAggregationInfoDTO> BidListsResponseAggregationInfoDTO;

    /**
     * Default constructor for BidListsResponse.
     * Logs the creation of an instance of BidListsResponse.
     */
    public BidListsResponse() {
        logger.debug("Creating instance of BidListsResponse.");
        // Lombok constructor
        logger.debug("Instance of BidListsResponse created.");
    }
}
