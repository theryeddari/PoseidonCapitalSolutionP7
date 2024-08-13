package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to rule names.
 */
@AllArgsConstructor
@Getter
public class RuleNameResponse {

    List<RuleNameResponseAggregationInfoDTO> ruleNameResponseResponseAggregationInfoDTO;

    /**
     * Default constructor for RuleNameResponse.
     */
    public RuleNameResponse() {
        // Lombok constructor
    }
}