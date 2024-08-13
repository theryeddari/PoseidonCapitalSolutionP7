package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Data Transfer Object (DTO) for handling individual user information.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseAggregationInfoDTO {
    private Integer id;
    private String username;
    private String fullname;
    private String role;
}