package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to trades.
 */
@AllArgsConstructor
@Getter
public class TradeResponse {

    private List<TradeResponseAggregationInfoDTO> tradeResponseAggregationInfoDTO;

    /**
     * Default constructor for TradeResponse.
     */
    public TradeResponse() {
        // Lombok will generate the constructor
    }
}