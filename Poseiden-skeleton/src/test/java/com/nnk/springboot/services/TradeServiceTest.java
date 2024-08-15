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

        Trade savedTrade = tradeService.tradeSave(trade);

        assertEquals(trade, savedTrade);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testTradeSave_Exception() {
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenThrow(new RuntimeException());

        assertThrows(TradeSaveException.class, () -> tradeService.tradeSave(trade));
    }

    @Test
    void testGetTradeById() throws TradeFindByIdException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade response = tradeService.tradeFindById(1);

        assertEquals((byte) 1, response.getTradeId());
    }

    @Test
    void testGetTradeById_Exception() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TradeFindByIdException.class, () -> tradeService.tradeFindById(1));
    }

    @Test
    void testTradeUpdate_Success() throws TradeUpdateException {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.save(trade)).thenReturn(trade);
        tradeService.tradeUpdate(1, trade);

        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testTradeUpdate_IDMismatch() {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        Exception exception = assertThrows(TradeUpdateException.class, () -> tradeService.tradeUpdate(2, trade));
        assertEquals(TradeIncoherenceBetweenObjectException.class, exception.getCause().getClass());
    }

    @Test
    void testTradeUpdate_Exception() {
        Trade trade = new Trade();
        trade.setTradeId((byte) 1);

        when(tradeRepository.save(trade)).thenThrow(new RuntimeException());

        assertThrows(TradeUpdateException.class, () -> tradeService.tradeUpdate(1, trade));
    }

    @Test
    void testDeleteTrade() throws TradeDeleteException {
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.tradeDelete(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTrade_Exception() {
        doThrow(new RuntimeException()).when(tradeRepository).deleteById(1);

        assertThrows(TradeDeleteException.class, () -> tradeService.tradeDelete(1));
    }
}
