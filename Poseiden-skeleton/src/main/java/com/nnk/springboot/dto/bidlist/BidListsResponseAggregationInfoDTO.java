package com.nnk.springboot.dto.bidlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class BidListsResponseAggregationInfoDTO {
    private String id;
    private String account;
    private String type;
    private String bidQuantity;

    public BidListsResponseAggregationInfoDTO() {
        //lombok constructor
    }
}
