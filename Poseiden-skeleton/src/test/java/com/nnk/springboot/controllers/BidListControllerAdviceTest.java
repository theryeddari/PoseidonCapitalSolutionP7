package com.nnk.springboot.controllers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;
import static com.nnk.springboot.exceptions.BidListServiceException.*;

@ExtendWith(MockitoExtension.class)
public class BidListControllerAdviceTest {

    BidListControllerAdvice bidListControllerAdvice = new BidListControllerAdvice();

    @Test
    void handleBidListServiceException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = bidListControllerAdvice.handleBidListServiceException(new BidListAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(BID_LIST_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }
    @Test
    void handleBidBidListSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = bidListControllerAdvice.handleBidBidListSaveException(new BidListSaveException(runtimeException));

        Assertions.assertTrue(result.contains(BID_LIST_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleBidListFindByIdException() {
        BidListNotFoundException bidListNotFoundException = new BidListNotFoundException();
        String result = bidListControllerAdvice.handleBidBidListSaveException(new BidListSaveException(bidListNotFoundException));

        Assertions.assertTrue(result.contains(BID_LIST_FIND_BY_ID));
    }

    @Test
    void handleBidBidListSaveException_WithIdVerification_Failed(){
        BidListIncoherenceBetweenObject bidListIncoherenceBetweenObject = new BidListIncoherenceBetweenObject();
        String result = bidListControllerAdvice.handleBidBidListSaveException(new BidListSaveException(bidListIncoherenceBetweenObject));

        Assertions.assertTrue(result.contains(BID_LIST_INCOHERENCE_BETWEEN_OBJET_EXCEPTION));
    }
}
