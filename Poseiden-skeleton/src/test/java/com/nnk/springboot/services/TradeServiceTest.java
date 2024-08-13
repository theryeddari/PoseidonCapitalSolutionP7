package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.dto.TradeResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.TradeServiceException.*;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
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
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Test
    void testTradeAggregationInfo() throws TradeAggregationInfoException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(10D);

        List<Trade> trades = List.of(trade);
        when(tradeRepository.findAll()).thenReturn(trades);

        TradeResponse tradeResponse = tradeService.tradeAggregationInfo();

        List<TradeResponseAggregationInfoDTO> tradeResponseAggregationInfoDTO = tradeResponse.getTradeResponseAggregationInfoDTO();

        assertEquals(String.valueOf(trade.getTradeId()), tradeResponseAggregationInfoDTO.getFirst().getTradeId());
        assertEquals(trade.getAccount(), tradeResponseAggregationInfoDTO.getFirst().getAccount());
        assertEquals(trade.getType(), tradeResponseAggregationInfoDTO.getFirst().getType());
        assertEquals(String.valueOf(trade.getBuyQuantity()), tradeResponseAggregationInfoDTO.getFirst().getBuyQuantity());
    }

    @Test
    void testTradeAggregationInfo_Failed() {
        when(tradeRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(TradeAggregationInfoException.class, () -> tradeService.tradeAggregationInfo());
    }


    @Test
    void testTradeFindById() throws TradeFindByIdException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(10D);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade response = tradeService.tradeFindById(1);

        assertEquals((byte) 1, response.getTradeId());
    }

    @Test
    void testTradeFindById_TradeNotFoundException() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(TradeFindByIdException.class, () -> tradeService.tradeFindById(1));
        assertEquals(TradeNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testTradeSaveOverloadWithIdVerification_Success() throws TradeSaveException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.save(trade)).thenReturn(trade);

        tradeService.tradeSave(1, trade);

        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testTradeSaveOverloadWithIdVerification_Failed() {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        Exception exception = assertThrows(TradeSaveException.class, () -> tradeService.tradeSave(2, trade));

        assertEquals(TradeIncoherenceBetweenObject.class, exception.getCause().getClass());

        verify(tradeRepository, never()).save(trade);
    }

    @Test
    void testTradeSaveException() {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.save(trade)).thenThrow(new RuntimeException());

        assertThrows(TradeSaveException.class, () -> tradeService.tradeSave(1, trade));
    }

    @Test
    void testTradeDelete() throws TradeDeleteException {
        doNothing().when(tradeRepository).deleteById(1);
        tradeService.tradeDelete(1);
        verify(tradeRepository).deleteById(1);
    }

    @Test
    void testTradeDeleteException() {
        doThrow(new RuntimeException()).when(tradeRepository).deleteById(anyInt());
        assertThrows(TradeDeleteException.class, () -> tradeService.tradeDelete(1));
    }
}
