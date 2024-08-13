package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for aggregating rule name response information.
 * <p>
 * This DTO is used to transfer rule name information between layers of the application.
 * It includes details about the rule name such as id, name, description, jsonTemplate, sql, and sqlPart.
 * </p>
 */
@Getter
@AllArgsConstructor
public class RuleNameResponseAggregationInfoDTO {

    /**
     * The unique identifier for the rule name.
     */
    private String id;

    /**
     * The name associated with the rule.
     */
    private String name;

    /**
     * The description of the rule.
     */
    private String description;

    /**
     * The JSON associated with the rule.
     */
    private String json;

    /**
     * The template associated with the rule.
     */
    private String template;

    /**
     * The SQL query or statement related to the rule.
     */
    private String sqlStr;

    /**
     * The SQL part or fragment related to the rule.
     */
    private String sqlPart;

    /**
     * Default constructor for RuleNameResponseAggregationInfoDTO.
     * <p>
     * This constructor is provided by Lombok and initializes a new instance of
     * RuleNameResponseAggregationInfoDTO with default values.
     * </p>
     */
    public RuleNameResponseAggregationInfoDTO() {
        // Lombok constructor
    }
}