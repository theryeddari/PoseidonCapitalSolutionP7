package com.nnk.springboot.controllers.bidlist;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListsResponse;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.nnk.springboot.exceptions.BidListServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListControllerTest {

    @InjectMocks
    BidListController bidListController;

    @Mock
    BidListService bidListService;

    @Mock
    Model model;

    @Test
    void testHome() throws BidListAggregationInfoException {
        BidListsResponse bidListsResponse = new BidListsResponse();

        when(bidListService.bidListAggregationInfo()).thenReturn(bidListsResponse);

        String viewName = bidListController.home(model);

        verify(bidListService, times(1)).bidListAggregationInfo();

        assertEquals("bidList/list", viewName);

        verify(model, times(1)).addAttribute(eq("bidLists"), eq(bidListsResponse.getBidListsResponseAggregationInfoDTO()));
    }

    @Test
    void testAddBidForm() {
        BidList bidList = new BidList();
        String viewName = bidListController.addBidForm(bidList, model);

        assertEquals("bidList/add", viewName);
        verify(model, times(1)).addAttribute(eq("bidList"), eq(bidList));
    }

    @Test
    void testValidate_True() throws BidListSaveException {
        BidList bidList = new BidList();
        BindingResult bindingResult = new BeanPropertyBindingResult(bidList, "bidList");

        when(bidListService.bidListSave(bidList)).thenReturn(bidList);
        String viewName = bidListController.validate(bidList, bindingResult, model);

        assertEquals("redirect:/home/bidList/list", viewName);
        verify(bidListService, times(1)).bidListSave(bidList);
        verify(model, never()).addAttribute(eq("bidList"), eq(bidList));

    }

    @Test
    void testValidate_FalseTest() throws BidListSaveException {
        BidList bidList = new BidList();
        BindingResult bindingResult = new BeanPropertyBindingResult(bidList, "bidList");

        bindingResult.rejectValue("account", "error.bidList", "Account is required");

        String viewName = bidListController.validate(bidList, bindingResult, model);

        assertEquals("bidList/add", viewName);
        verify(model, times(1)).addAttribute(eq("bidList"), eq(bidList));

        verify(bidListService, never()).bidListSave(bidList);
    }

    @Test
    void testShowUpdateFormTest() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId((byte) 1);

        when(bidListService.bidListFindById(1)).thenReturn(bidList);

        String viewName = bidListController.showUpdateForm(1, model);

        verify(bidListService, times(1)).bidListFindById(1);

        assertEquals("bidList/update", viewName);
        verify(model, times(1)).addAttribute(eq("bidList"), eq(bidList));
    }

    @Test
    void testUpdateBid_ValidateTrue() throws BidListUpdateException {
        BidList bidList = new BidList();
        BindingResult bindingResult = new BeanPropertyBindingResult(bidList, "bidList");

        doNothing().when(bidListService).bidListUpdate(1, bidList);

        String viewName = bidListController.updateBid(1, bidList, bindingResult, model);

        assertEquals("redirect:/home/bidList/list", viewName);
        verify(bidListService, times(1)).bidListUpdate(1, bidList);
    }

    @Test
    void testUpdateBid_ValidateFalse() throws BidListUpdateException {
        BidList bidList = new BidList();
        BindingResult bindingResult = new BeanPropertyBindingResult(bidList, "bidList");

        bindingResult.rejectValue("account", "error.bidList", "Account is required");

        String viewName = bidListController.updateBid(1, bidList, bindingResult, model);

        assertEquals("bidList/update", viewName);
        verify(model, times(1)).addAttribute(eq("bidList"), eq(bidList));
    }

    @Test
    void testDeleteBid() throws BidListDeleteException {
        doNothing().when(bidListService).bidListDelete(1);

        String viewName = bidListController.deleteBid(1);

        verify(bidListService, times(1)).bidListDelete(1);
        assertEquals("redirect:/home/bidList/list", viewName);
    }

}
