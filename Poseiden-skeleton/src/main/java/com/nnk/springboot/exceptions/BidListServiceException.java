package com.nnk.springboot.exceptions;


import static com.nnk.springboot.constants.ConstantsExceptions.*;

public class BidListServiceException extends Exception {

    public BidListServiceException(String message) {
        super(message);
    }

    public BidListServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class BidListAggregationInfoException extends BidListServiceException {

        public BidListAggregationInfoException(Exception cause) {
            super(BID_LIST_AGGREGATION_INFO_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
}
