package com.nnk.springboot.dto.bidlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to curve point.
 */
@AllArgsConstructor
@Getter
public class CurvePointResponse {

    private static final Logger logger = LoggerFactory.getLogger(CurvePointResponse.class);

    List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTO;

    /**
     * Default constructor forCurvePointResponse.
     * Logs the creation of an instance of CurvePointResponse.
     */
    public CurvePointResponse() {
        logger.debug("Creating instance of BidListsResponse.");
        // Lombok constructor
        logger.debug("Instance of BidListsResponse created.");
    }
}
