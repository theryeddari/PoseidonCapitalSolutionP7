package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BidTests {

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void testBidListCreation() {
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        bid = bidListRepository.save(bid);
        assertNotNull(bid.getBidListId());
        assertEquals(10d, bid.getBidQuantity(), 0.001d);
    }

    @Test
    public void testBidListUpdate() {
        // Create and save a BidList
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);
        bid = bidListRepository.save(bid);

        // Update the BidList
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        assertEquals(20d, bid.getBidQuantity(), 0.001d);
    }

    @Test
    public void testFindAllBidLists() {
        List<BidList> listResult = bidListRepository.findAll();
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void testDeleteBidList() {
        // Create and save a BidList
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);
        bid = bidListRepository.save(bid);

        // Delete the BidList
        int id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        assertFalse(bidList.isPresent());
    }
}
