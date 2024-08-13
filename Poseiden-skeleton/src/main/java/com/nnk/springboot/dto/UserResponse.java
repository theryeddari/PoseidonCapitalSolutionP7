package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A Data Transfer Object (DTO) for handling responses related to users.
 */
@AllArgsConstructor
@Getter
public class UserResponse {

    private List<UserResponseAggregationInfoDTO> userResponseAggregationInfoDTO;

    /**
     * Default constructor for UserResponse.
     */
    public UserResponse() {
        // Lombok will generate the constructor
    }
}