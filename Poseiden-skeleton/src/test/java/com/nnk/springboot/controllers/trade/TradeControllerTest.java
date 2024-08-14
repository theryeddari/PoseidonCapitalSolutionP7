package com.nnk.springboot.controllers.trade;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.nnk.springboot.exceptions.TradeServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

    @InjectMocks
    TradeController tradeController;

    @Mock
    TradeService tradeService;

    @Mock
    Model model;

    @Test
    void testHome() throws TradeAggregationInfoException {
        TradeResponse tradeResponse = new TradeResponse();

        when(tradeService.tradeAggregationInfo()).thenReturn(tradeResponse);

        String viewName = tradeController.home(model);

        verify(tradeService, times(1)).tradeAggregationInfo();

        assertEquals("trade/list", viewName);

        verify(model, times(1)).addAttribute(eq("trades"), eq(tradeResponse.getTradeResponseAggregationInfoDTO()));
    }

    @Test
    void testAddTradeForm() {
        Trade trade = new Trade();
        String viewName = tradeController.addTradeForm(trade, model);

        assertEquals("trade/add", viewName);
        verify(model, times(1)).addAttribute(eq("trade"), eq(trade));
    }

    @Test
    void testValidate_True() throws TradeSaveException {
        Trade trade = new Trade();
        BindingResult bindingResult = new BeanPropertyBindingResult(trade, "trade");

        when(tradeService.tradeSave(trade)).thenReturn(trade);

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService, times(1)).tradeSave(trade);
        verify(model, never()).addAttribute(eq("trade"), eq(trade));
    }

    @Test
    void testValidate_False() throws TradeSaveException {
        Trade trade = new Trade();
        BindingResult bindingResult = new BeanPropertyBindingResult(trade, "trade");

        bindingResult.rejectValue("buyQuantity", "error.trade", "Account is required");

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("trade/add", viewName);
        verify(model, times(1)).addAttribute(eq("trade"), eq(trade));

    }

    @Test
    void testShowUpdateForm() throws TradeFindByIdException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeService.tradeFindById(1)).thenReturn(trade);

        String viewName = tradeController.showUpdateForm(1, model);

        verify(tradeService, times(1)).tradeFindById(1);

        assertEquals("trade/update", viewName);
        verify(model, times(1)).addAttribute(eq("trade"), eq(trade));
    }

    @Test
    void testUpdateTrade_ValidateTrue() throws TradeSaveException {
        Trade trade = new Trade();
        BindingResult bindingResult = new BeanPropertyBindingResult(trade, "trade");

        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService, times(1)).tradeSave(1, trade);
    }

    @Test
    void testUpdateTrade_ValidateFalse() throws TradeSaveException {
        Trade trade = new Trade();
        BindingResult bindingResult = new BeanPropertyBindingResult(trade, "trade");

        bindingResult.rejectValue("buyQuantity", "error.trade", "Account is required");

        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        assertEquals("trade/update", viewName);
        verify(model, times(1)).addAttribute(eq("trade"), eq(trade));

    }

    @Test
    void testDeleteTrade() throws TradeDeleteException {
        doNothing().when(tradeService).tradeDelete(1);

        String viewName = tradeController.deleteTrade(1);

        verify(tradeService, times(1)).tradeDelete(1);
        assertEquals("redirect:/trade/list", viewName);
    }
}
