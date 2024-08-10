package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.dto.bidlist.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.BidListServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @InjectMocks
    BidListService bidListService;

    @Mock
    BidListRepository bidListRepository;

    @Test
    void testBidListAggregationInfo() throws BidListAggregationInfoException {
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
    void testBidListAggregationInfo_Failed(){
        when(bidListRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(BidListAggregationInfoException.class, () -> bidListService.bidListAggregationInfo());
    }
    @Test
    void testBidListSave() throws BidListSaveException {
        BidList bidList = new BidList();
        BindingResult bindingResult = new BeanPropertyBindingResult(bidList, "bidList");

        bindingResult.rejectValue("account", "error.bidList", "Account is required");

        bidListService.bidListSave(bidList,bindingResult);

        verify(bidListRepository, never()).save(bidList);
    }

    @Test
    void testBidListFindById() throws FindBidListById {
        BidList bidList = new BidList();

        bidList.setBidListId((byte) 1);
        bidList.setAccount("user");
        bidList.setType("USER");
        bidList.setBidQuantity(10D);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList response = bidListService.BidListFindById(1);

        assertEquals(1, (byte) response.getBidListId());
    }

    @Test
    void testBidListFindById_BidListNotFoundException() {

        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(FindBidListById.class, () -> bidListService.BidListFindById(1));
        assertEquals(BidListNotFoundException.class, exception.getCause().getClass());
    }
}

