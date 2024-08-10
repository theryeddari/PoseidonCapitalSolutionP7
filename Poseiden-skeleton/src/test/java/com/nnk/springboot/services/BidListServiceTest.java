package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.dto.bidlist.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.BidListServiceException;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @InjectMocks
    BidListService bidListService;

    @Mock
    BidListRepository bidListRepository;

    @Test
    void bidListAggregationInfo() throws BidListServiceException.BidListAggregationInfoException {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);
        bidList.setAccount("user");
        bidList.setType("USER");
        bidList.setBidQuantity(10D);

        List<BidList> bidLists = List.of(bidList);
        when(bidListRepository.findAll()).thenReturn(bidLists);

        BidListsResponse bidListsResponse = bidListService.bidListAggregationInfo();

        List<BidListsResponseAggregationInfoDTO> bidListsResponseAggregationInfoDTO = bidListsResponse.getBidListsResponseAggregationInfoDTO();

        assertEquals(String.valueOf(bidList.getBidListId()), bidListsResponseAggregationInfoDTO.getFirst().getId());
        assertEquals(bidList.getAccount(), bidListsResponseAggregationInfoDTO.getLast().getAccount());
        assertEquals(bidList.getType(), bidListsResponseAggregationInfoDTO.getFirst().getType());
        assertEquals(String.valueOf(bidList.getBidQuantity()), bidListsResponseAggregationInfoDTO.getFirst().getBidQuantity());

    }

    @Test
    void bidListAggregationInfo_Failed(){
        when(bidListRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(BidListServiceException.BidListAggregationInfoException.class, () -> bidListService.bidListAggregationInfo());
    }
}
