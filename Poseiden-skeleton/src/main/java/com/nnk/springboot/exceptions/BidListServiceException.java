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

    public static class BidListSaveException extends BidListServiceException {
        public BidListSaveException(Exception cause) {
            super(BID_LIST_SAVE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);

        }
    }

    public static class FindBidListById extends BidListServiceException {
        public FindBidListById(Exception cause) {
            super(FIND_BID_LIST_BY_ID + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    public static class BidListNotFoundException extends BidListServiceException {
        public BidListNotFoundException() {
            super(FIND_BID_LIST_BY_ID);
        }
    }

    public static class BidListIncoherenceBetweenObject extends BidListServiceException {
        public BidListIncoherenceBetweenObject() {
            super(BID_LIST_INCOHERENCE_BETWEEN_OBJET_EXCEPTION);
        }
    }
}
