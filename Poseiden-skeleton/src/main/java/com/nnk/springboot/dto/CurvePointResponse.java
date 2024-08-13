package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to curve point.
 */
@AllArgsConstructor
@Getter
public class CurvePointResponse {
    List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTO;

    /**
     * Default constructor forCurvePointResponse.
     * Logs the creation of an instance of CurvePointResponse.
     */
    public CurvePointResponse() {
        // Lombok constructor
    }
}
