package com.nnk.springboot.constants;

public class ConstantsExceptions {

    public static final String MORE_INFO = ", additional information: ";

    //BidList Exception
    public static final String BID_LIST_AGGREGATION_INFO_EXCEPTION = "An error occurred while aggregating bid infos";
    public static final String BID_LIST_SAVE_EXCEPTION = "An error occurred while saving bid infos";
    public static final String BID_LIST_FIND_BY_ID_EXCEPTION = "An error occurred while finding bid list by id";
    public static final String BID_LIST_NOT_FOUND_EXCEPTION = "Not found bid list by id";
    public static final String BID_LIST_INCOHERENCE_BETWEEN_OBJET_EXCEPTION = "An error occurred while checking the identifier of the object to save and that to theoretically save";
    public static final String BID_LIST_DELETE_EXCEPTION = "An error occurred while deleting bid list";

    //CurvePoint Exception
    public static final String CURVE_POINT_AGGREGATION_INFO_EXCEPTION = "An error occurred while aggregating curve point infos"; ;
    public static final String CURVE_POINT_SAVE_EXCEPTION = "An error occurred while saving curve point infos";
    public static final String CURVE_POINT_FIND_BY_ID_EXCEPTION = "An error occurred while finding curve point by id";
    public static final String CURVE_POINT_NOT_FOUND_EXCEPTION = "Not Found curve point by id";
    public static final String CURVE_POINT_INCOHERENCE_BETWEEN_OBJET_EXCEPTION = "An error occured while checking the identifier of the object to save and that to theoretically save";
    public static final String CURVE_POINT_DELETE_EXCEPTION = "An error occurred while deleting curve point by id";
}
