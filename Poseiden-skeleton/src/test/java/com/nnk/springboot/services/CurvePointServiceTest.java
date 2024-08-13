package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointResponse;
import com.nnk.springboot.dto.CurvePointResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.CurvePointServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @InjectMocks
    CurveService curveService;

    @Mock
    CurvePointRepository curvePointRepository;

    @Test
    void testCurvePointAggregationInfo() throws CurvePointAggregationInfoException {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        curvePoint.setTerm(10d);
        curvePoint.setValue(20d);

        List<CurvePoint> curvePoints = List.of(curvePoint);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        CurvePointResponse curvePointResponse = curveService.curvePointAggregationInfo();

        List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTOS = curvePointResponse.getCurvePointResponseAggregationInfoDTO();

        assertEquals(String.valueOf(curvePoint.getCurveId()), curvePointResponseAggregationInfoDTOS.getFirst().getCurveId());
        assertEquals(String.valueOf(curvePoint.getTerm()), curvePointResponseAggregationInfoDTOS.getFirst().getTerm());
        assertEquals(String.valueOf(curvePoint.getValue()), curvePointResponseAggregationInfoDTOS.getFirst().getValue());

    }

    @Test
    void testCurvePointAggregationInfo_Failed(){
        when(curvePointRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(CurvePointAggregationInfoException.class, () -> curveService.curvePointAggregationInfo());
    }

    @Test
    void testCurvePointSave_BindingSuccess() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curveService.curvePointSave(curvePoint,bindingResult);

        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void testCurvePointSave_BindingError() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        bindingResult.rejectValue("term", "error.curvePoint", "Account is required");

        curveService.curvePointSave(curvePoint,bindingResult);

        verify(curvePointRepository, never()).save(curvePoint);
    }

    @Test
    void testCurvePointFindById() throws CurvePointFindByIdException {
        CurvePoint curvePoint = new CurvePoint();

        curvePoint.setCurveId((byte) 1);
        curvePoint.setTerm(10d);
        curvePoint.setValue(20d);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint response = curveService.CurvePointFindById(1);

        assertEquals(1, (byte) response.getCurveId());
    }

    @Test
    void testCurvePointFindById_CurvePointNotFoundException() {

        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(CurvePointFindByIdException.class, () -> curveService.CurvePointFindById(1));
        assertEquals(CurvePointNotFoundException.class, exception.getCause().getClass());
    }
    @Test
    void testCurvePointSaveOverloadWithIdVerification_Success() throws CurvePointSaveException {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curveService.curvePointSave(1,curvePoint,bindingResult);

        verify(curvePointRepository, times(1)).save(curvePoint);

    }

    @Test
    void testCurvePointSaveOverloadWithIdVerification_Failed() {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        Exception exception = assertThrows(CurvePointSaveException.class, () -> curveService.curvePointSave(2,curvePoint,bindingResult));

        assertEquals(CurvePointIncoherenceBetweenObject.class, exception.getCause().getClass());

        verify(curvePointRepository, never()).save(curvePoint);

    }
    @Test
    void testCurvePointSaveException(){
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointRepository.save(curvePoint)).thenThrow(new RuntimeException());

        assertThrows(CurvePointSaveException.class, () -> curveService.curvePointSave(1,curvePoint,bindingResult));
    }
    @Test
    void testCurvePointDelete() throws CurvePointDeleteException {
        doNothing().when(curvePointRepository).deleteById(1);
        curveService.curvePointDelete(1);
        verify(curvePointRepository).deleteById(1);
    }
    @Test
    void testCurvePointDeleteException(){
        doThrow(new RuntimeException()).when(curvePointRepository).deleteById(anyInt());
        assertThrows(CurvePointDeleteException.class, () -> curveService.curvePointDelete(1));
    }
}

