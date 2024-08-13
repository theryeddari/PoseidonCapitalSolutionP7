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
	public void tradeTest() {
		// Create a new Trade instance
		Trade trade = new Trade();
		trade.setAccount("Trade Account");
		trade.setType("Type");
		trade.setBuyQuantity(2d);

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
		assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertEquals("Trade Account Update", trade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		Integer id = Integer.valueOf(trade.getTradeId());
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}