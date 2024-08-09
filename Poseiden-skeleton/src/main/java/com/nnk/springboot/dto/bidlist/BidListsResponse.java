package com.nnk.springboot.dto.bidlist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BidListsResponse {

    List<BidListsResponseAggregationInfoDTO> BidListsResponseAggregationInfoDTO;

    public BidListsResponse() {
        //lombok constructor
    }
}
