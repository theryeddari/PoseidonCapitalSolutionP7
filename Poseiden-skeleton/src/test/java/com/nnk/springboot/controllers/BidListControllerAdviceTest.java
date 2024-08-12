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
    void handleBidListAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = bidListControllerAdvice.handleBidListAggregationInfoException(new BidListAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(BID_LIST_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }
    @Test
    void handleBidListSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = bidListControllerAdvice.handleBidListSaveException(new BidListSaveException(runtimeException));

        Assertions.assertTrue(result.contains(BID_LIST_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleBidListFindByIdException() {
        String result = bidListControllerAdvice.handleBidListFindByIdException(new BidListFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(BID_LIST_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }
    @Test
    void handleBidListFindById_BidListNotFoundException() {
        BidListNotFoundException bidListNotFoundException = new BidListNotFoundException();
        String result = bidListControllerAdvice.handleBidListFindByIdException(new BidListFindByIdException(bidListNotFoundException));

        Assertions.assertTrue(result.contains(BID_LIST_FIND_BY_ID_EXCEPTION));
    }

    @Test
    void handleBidListSaveException_WithIdVerification_Failed(){
        BidListIncoherenceBetweenObject bidListIncoherenceBetweenObject = new BidListIncoherenceBetweenObject();
        String result = bidListControllerAdvice.handleBidListSaveException(new BidListSaveException(bidListIncoherenceBetweenObject));

        Assertions.assertTrue(result.contains(BID_LIST_INCOHERENCE_BETWEEN_OBJET_EXCEPTION));
    }

    @Test
    void handleBidListDeleteException(){
        BidListDeleteException bidListDeleteException = new BidListDeleteException(new RuntimeException());
        String result = bidListControllerAdvice.handleBidListDeleteException(bidListDeleteException);

        Assertions.assertTrue(result.contains(BID_LIST_DELETE_EXCEPTION + MORE_INFO));
    }
}
