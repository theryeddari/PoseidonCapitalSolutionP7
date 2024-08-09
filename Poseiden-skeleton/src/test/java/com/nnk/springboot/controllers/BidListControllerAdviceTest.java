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
}
