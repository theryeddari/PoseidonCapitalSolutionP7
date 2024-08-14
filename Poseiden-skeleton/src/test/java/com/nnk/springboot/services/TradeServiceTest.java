package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.dto.TradeResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.TradeServiceException.*;
import com.nnk.springboot.repositories.TradeRepository;
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

        TradeResponse response = tradeService.tradeAggregationInfo();

        List<TradeResponseAggregationInfoDTO> dtos = response.getTradeResponseAggregationInfoDTO();

        assertEquals("1", dtos.getFirst().getTradeId());
        assertEquals("Trade Account", dtos.getFirst().getAccount());
        assertEquals("Type", dtos.getFirst().getType());
        assertEquals("10.0", dtos.getFirst().getBuyQuantity());
    }

    @Test
    void testTradeAggregationInfo_Failed() {
        when(tradeRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(TradeAggregationInfoException.class, () -> tradeService.tradeAggregationInfo());
    }

    @Test
    void testTradeSave_Success() throws TradeSaveException {
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenReturn(trade);

        tradeService.tradeSave(trade);

        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testTradeSaveException() {
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenThrow(new RuntimeException());
        assertThrows(TradeSaveException.class, () -> tradeService.tradeSave(trade));
    }

    @Test
    void testTradeFindById() throws TradeFindByIdException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade response = tradeService.tradeFindById(1);

        assertEquals((byte) 1, response.getTradeId());
    }

    @Test
    void testTradeFindById_Exception() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TradeFindByIdException.class, () -> tradeService.tradeFindById(1));
    }

    @Test
    void testTradeDelete() throws TradeDeleteException {
        doNothing().when(tradeRepository).deleteById(1);
        tradeService.tradeDelete(1);
        verify(tradeRepository).deleteById(1);
    }

    @Test
    void testTradeDelete_Exception() {
        doThrow(new RuntimeException()).when(tradeRepository).deleteById(anyInt());
        assertThrows(TradeDeleteException.class, () -> tradeService.tradeDelete(1));
    }
}
