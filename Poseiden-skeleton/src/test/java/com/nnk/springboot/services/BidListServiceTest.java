package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListsResponse;
import com.nnk.springboot.dto.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.bidListSave(bidList);

        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void testBidListSaveException() {
        BidList bidList = new BidList();
        when(bidListRepository.save(bidList)).thenThrow(new RuntimeException());

        assertThrows(BidListSaveException.class, () -> bidListService.bidListSave(bidList));

    }


    @Test
    void testBidListFindById() throws BidListFindByIdException {
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

        Exception exception = Assertions.assertThrows(BidListFindByIdException.class, () -> bidListService.BidListFindById(1));
        assertEquals(BidListNotFoundException.class, exception.getCause().getClass());
    }
    @Test
    void testBidListUpdateOverloadWithIdVerification_Success() throws BidListUpdateException {

        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.bidListUpdate(1,bidList);

        verify(bidListRepository, times(1)).save(bidList);

    }

    @Test
    void testBidListUpdateOverloadWithIdVerification_Failed() {

        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        Exception exception = assertThrows(BidListUpdateException.class, () -> bidListService.bidListUpdate(2,bidList));

        assertEquals(BidListIncoherenceBetweenObjectException.class, exception.getCause().getClass());

        verify(bidListRepository, never()).save(bidList);

    }
    @Test
    void testBidListUpdateException(){
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        when(bidListRepository.save(bidList)).thenThrow(new RuntimeException());

        assertThrows(BidListUpdateException.class, () -> bidListService.bidListUpdate(1,bidList));
    }
    @Test
    void testBidListDelete() throws BidListDeleteException {
        doNothing().when(bidListRepository).deleteById(1);
        bidListService.bidListDelete(1);
        verify(bidListRepository).deleteById(1);
    }
    @Test
    void testBidListDeleteException(){
        doThrow(new RuntimeException()).when(bidListRepository).deleteById(anyInt());
        assertThrows(BidListDeleteException.class, () -> bidListService.bidListDelete(1));
    }
}

