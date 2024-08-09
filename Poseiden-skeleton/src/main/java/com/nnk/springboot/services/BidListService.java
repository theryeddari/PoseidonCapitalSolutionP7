package com.nnk.springboot.services;

import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.dto.bidlist.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nnk.springboot.exceptions.BidListServiceException.*;

@Service
public class BidListService {

    final BidListRepository BidListRepository;

    public BidListService(BidListRepository BidListRepository) {
        this.BidListRepository = BidListRepository;
    }


    public BidListsResponse bidListAggregationInfo() throws BidListAggregationInfoException {
        try {
            List<BidListsResponseAggregationInfoDTO> bidListsResponseAggregationInfoDTO = List.of(new BidListsResponseAggregationInfoDTO());
            return new BidListsResponse(bidListsResponseAggregationInfoDTO);
        } catch (Exception e) {
            throw new BidListAggregationInfoException(e);
        }
    }
}
