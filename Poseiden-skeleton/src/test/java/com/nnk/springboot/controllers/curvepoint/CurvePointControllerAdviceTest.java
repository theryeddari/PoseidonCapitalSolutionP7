package com.nnk.springboot.controllers.curvepoint;


import com.nnk.springboot.controllers.CurveControllerAdvice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;
import static com.nnk.springboot.exceptions.CurvePointServiceException.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointControllerAdviceTest {

    CurveControllerAdvice curveControllerAdvice = new CurveControllerAdvice();

    @Test
    void handleCurvePointAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = curveControllerAdvice.handleCurvePointAggregationInfoException(new CurvePointAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(CURVE_POINT_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }
    @Test
    void handleBidListSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = curveControllerAdvice.handleCurvePointSaveException(new CurvePointSaveException(runtimeException));

        Assertions.assertTrue(result.contains(CURVE_POINT_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleCurvePointFindByIdException() {
        String result = curveControllerAdvice.handleCurvePointFindByIdException(new CurvePointFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(CURVE_POINT_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }
    @Test
    void handleCurvePointFindById_CurvePointNotFoundException() {
        CurvePointNotFoundException curvePointNotFoundException = new CurvePointNotFoundException();
        String result = curveControllerAdvice.handleCurvePointFindByIdException(new CurvePointFindByIdException(curvePointNotFoundException));

        Assertions.assertTrue(result.contains(CURVE_POINT_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleCurvePointSaveException_WithIdVerification_Failed(){
        CurvePointIncoherenceBetweenObject curvePointIncoherenceBetweenObject = new CurvePointIncoherenceBetweenObject();
        String result = curveControllerAdvice.handleCurvePointSaveException(new CurvePointSaveException(curvePointIncoherenceBetweenObject));

        Assertions.assertTrue(result.contains(CURVE_POINT_INCOHERENCE_BETWEEN_OBJET_EXCEPTION));
    }

    @Test
    void handleBidListDeleteException(){
        CurvePointDeleteException curvePointDeleteException = new CurvePointDeleteException(new RuntimeException());
        String result = curveControllerAdvice.handleCurvePointDeleteException(curvePointDeleteException);

        Assertions.assertTrue(result.contains(CURVE_POINT_DELETE_EXCEPTION + MORE_INFO));
    }
}
