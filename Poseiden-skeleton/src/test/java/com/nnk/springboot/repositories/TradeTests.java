package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeTests {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void testTradeCreation() {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(2d);

        trade = tradeRepository.save(trade);
        assertNotNull(trade.getTradeId());
        assertEquals("Trade Account", trade.getAccount());
    }

    @Test
    public void testTradeUpdate() {
        // Create and save a trade
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(2d);
        trade = tradeRepository.save(trade);

        // Update the trade
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertEquals("Trade Account Update", trade.getAccount());
    }

    @Test
    public void testFindAllTrades() {
        List<Trade> listResult = tradeRepository.findAll();
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void testDeleteTrade() {
        // Create and save a trade
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(2d);
        trade = tradeRepository.save(trade);

        // Delete the trade
        Integer id = Integer.valueOf(trade.getTradeId());
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(id);
        assertFalse(tradeList.isPresent());
    }
}
