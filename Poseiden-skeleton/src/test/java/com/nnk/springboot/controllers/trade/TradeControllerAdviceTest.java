package com.nnk.springboot.controllers.trade;

import com.nnk.springboot.controllers.TradeControllerAdvice;
import com.nnk.springboot.exceptions.TradeServiceException.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

@ExtendWith(MockitoExtension.class)
public class TradeControllerAdviceTest {

    TradeControllerAdvice tradeControllerAdvice = new TradeControllerAdvice();

    @Test
    void handleTradeAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = tradeControllerAdvice.handleTradeAggregationInfoException(new TradeAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(TRADE_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleTradeSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = tradeControllerAdvice.handleTradeSaveException(new TradeSaveException(runtimeException));

        Assertions.assertTrue(result.contains(TRADE_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleTradeFindByIdException() {
        String result = tradeControllerAdvice.handleTradeFindByIdException(new TradeFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(TRADE_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleTradeFindById_TradeNotFoundException() {
        TradeNotFoundException tradeNotFoundException = new TradeNotFoundException();
        String result = tradeControllerAdvice.handleTradeFindByIdException(new TradeFindByIdException(tradeNotFoundException));

        Assertions.assertTrue(result.contains(TRADE_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleTradeUpdateException() {
        String result = tradeControllerAdvice.handleTradeUpdateException(new TradeUpdateException(new RuntimeException()));

        Assertions.assertTrue(result.contains(TRADE_UPDATE_EXCEPTION));
    }

    @Test
    void handleTradeUpdateException_WithIdVerification_Failed() {
        TradeIncoherenceBetweenObjectException tradeIncoherenceBetweenObjectException = new TradeIncoherenceBetweenObjectException();
        String result = tradeControllerAdvice.handleTradeUpdateException(new TradeUpdateException(tradeIncoherenceBetweenObjectException));

        Assertions.assertTrue(result.contains(TRADE_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION));
    }

    @Test
    void handleTradeDeleteException() {
        TradeDeleteException tradeDeleteException = new TradeDeleteException(new RuntimeException());
        String result = tradeControllerAdvice.handleTradeDeleteException(tradeDeleteException);

        Assertions.assertTrue(result.contains(TRADE_DELETE_EXCEPTION + MORE_INFO));
    }
}
