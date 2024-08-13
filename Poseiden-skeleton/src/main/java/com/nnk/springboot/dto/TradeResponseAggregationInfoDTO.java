package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for individual trade information.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TradeResponseAggregationInfoDTO {

    private String tradeId;
    private String account;
    private String type;
    private String buyQuantity;

    // Add additional fields as required
}