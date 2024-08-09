package com.nnk.springboot.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.nnk.springboot.exceptions.BidListServiceException.*;

@ControllerAdvice
public class BidListControllerAdvice {

    @ExceptionHandler(BidListAggregationInfoException.class)
    public String handleBidListServiceException(BidListAggregationInfoException ex) {
            //TODO: complete logic Controller Advice response and adapt template to manage it
        return ex.getMessage();
    }
}
