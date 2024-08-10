package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.exceptions.BidListServiceException;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension .class)
public class BidListControllerTest {

    @InjectMocks
    BidListController bidListController;

    @Mock
    BidListService bidListService;

    @Mock
    Model model;

    @Test
    void homeTest() throws BidListServiceException.BidListAggregationInfoException {
        BidListsResponse bidListsResponse = new BidListsResponse();

        when(bidListService.bidListAggregationInfo()).thenReturn(bidListsResponse);

        String viewName = bidListController.home(model);

        verify(bidListService, times(1)).bidListAggregationInfo();

        Assertions.assertEquals("bidList/list", viewName);

        verify(model, times(1)).addAttribute(eq("bidLists"), eq(bidListsResponse.getBidListsResponseAggregationInfoDTO()));

    }

}
