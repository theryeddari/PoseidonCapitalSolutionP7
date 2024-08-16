package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListsResponse;
import com.nnk.springboot.dto.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.BidListServiceException.*;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    @Test
    void testBidListAggregationInfo() throws BidListAggregationInfoException {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);
        bidList.setAccount("user");
        bidList.setType("USER");
        bidList.setBidQuantity(10D);

        List<BidList> bidLists = List.of(bidList);
        when(bidListRepository.findAll()).thenReturn(bidLists);

        BidListsResponse response = bidListService.bidListAggregationInfo();

        List<BidListsResponseAggregationInfoDTO> dtos = response.getBidListsResponseAggregationInfoDTO();

        assertEquals("1", dtos.get(0).getId());
        assertEquals("user", dtos.get(0).getAccount());
        assertEquals("USER", dtos.get(0).getType());
        assertEquals("10.0", dtos.get(0).getBidQuantity());
    }

    @Test
    void testBidListAggregationInfo_Failed() {
        when(bidListRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(BidListAggregationInfoException.class, () -> bidListService.bidListAggregationInfo());
    }

    @Test
    void testBidListSave_Success() throws BidListSaveException {
        BidList bidList = new BidList();

        when(bidListRepository.save(bidList)).thenReturn(bidList);

        BidList savedBidList = bidListService.bidListSave(bidList);

        assertEquals(bidList, savedBidList);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void testBidListSave_Exception() {
        BidList bidList = new BidList();
        when(bidListRepository.save(bidList)).thenThrow(new RuntimeException());

        assertThrows(BidListSaveException.class, () -> bidListService.bidListSave(bidList));
    }

    @Test
    void testBidListFindById() throws BidListFindByIdException {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);
        bidList.setAccount("user");

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList response = bidListService.bidListFindById(1);

        assertEquals((byte) 1, response.getBidListId());
        assertEquals("user", response.getAccount());
    }

    @Test
    void testBidListFindById_Exception() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BidListFindByIdException.class, () -> bidListService.bidListFindById(1));
    }

    @Test
    void testBidListUpdate_Success() throws BidListUpdateException {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        when(bidListRepository.save(bidList)).thenReturn(bidList);
        bidListService.bidListUpdate(1, bidList);

        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void testBidListUpdate_IDMismatch() {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        Exception exception = assertThrows(BidListUpdateException.class, () -> bidListService.bidListUpdate(2, bidList));
        assertEquals(BidListIncoherenceBetweenObjectException.class, exception.getCause().getClass());
    }

    @Test
    void testBidListUpdate_Exception() {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        when(bidListRepository.save(bidList)).thenThrow(new RuntimeException());

        assertThrows(BidListUpdateException.class, () -> bidListService.bidListUpdate(1, bidList));
    }

    @Test
    void testBidListDelete() throws BidListDeleteException {
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.bidListDelete(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }

    @Test
    void testBidListDelete_Exception() {
        doThrow(new RuntimeException()).when(bidListRepository).deleteById(1);

        assertThrows(BidListDeleteException.class, () -> bidListService.bidListDelete(1));
    }
}
