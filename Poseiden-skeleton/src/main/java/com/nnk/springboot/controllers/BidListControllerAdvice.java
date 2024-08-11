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

    @ExceptionHandler(BidListSaveException.class)
    public String handleBidBidListSaveException(BidListSaveException ex) {
        if(ex.getCause() instanceof BidListIncoherenceBetweenObject){
            return ex.getCause().getMessage();
        }
        //TODO: complete logic Controller Advice response and adapt template to manage it
        return ex.getMessage();
    }

    @ExceptionHandler(BidListFindByIdException.class)
    public String handleBidListFindByIdException(BidListFindByIdException ex) {
        if(ex.getCause() instanceof BidListNotFoundException){
            return ex.getCause().getMessage();
        }
        //TODO: complete logic Controller Advice response and adapt template to manage it
        return ex.getMessage();
    }
}
