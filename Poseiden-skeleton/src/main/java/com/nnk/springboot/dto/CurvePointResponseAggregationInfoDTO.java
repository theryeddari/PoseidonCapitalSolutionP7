package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for aggregating bid list response information.
 * <p>
 * This DTO is used to transfer curve point list information between layers of the application.
 * It includes details about the curve point list such as id, term, value.
 * </p>
 */
@Getter
@AllArgsConstructor
public class CurvePointResponseAggregationInfoDTO {

    /**
     * The unique identifier for the curve point.
     */
    private String curveId;

    /**
     * The term associated with the curve point.
     */
    private String term;

    /**
     * The value of the curve point.
     */
    private String value;


    /**
     * Default constructor for CurvePointResponseAggregationInfoDTO.
     * <p>
     * This constructor is provided by Lombok and initializes a new instance of
     * CurvePointResponseAggregationInfoDTO with default values.
     * </p>
     */
    public CurvePointResponseAggregationInfoDTO() {
        // Lombok constructor
    }
}
