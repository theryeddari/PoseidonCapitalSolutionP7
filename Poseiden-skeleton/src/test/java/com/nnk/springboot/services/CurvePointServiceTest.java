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

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.CurvePointServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @InjectMocks
    CurvePointService curvePointService;

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

        CurvePointResponse curvePointResponse = curvePointService.curvePointAggregationInfo();

        List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTOs = curvePointResponse.getCurvePointResponseAggregationInfoDTO();

        assertEquals(String.valueOf(curvePoint.getCurveId()), curvePointResponseAggregationInfoDTOs.getFirst().getCurveId());
        assertEquals(String.valueOf(curvePoint.getTerm()), curvePointResponseAggregationInfoDTOs.getFirst().getTerm());
        assertEquals(String.valueOf(curvePoint.getValue()), curvePointResponseAggregationInfoDTOs.getFirst().getValue());
    }

    @Test
    void testCurvePointAggregationInfo_Failed() {
        when(curvePointRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(CurvePointAggregationInfoException.class, () -> curvePointService.curvePointAggregationInfo());
    }

    @Test
    void testCurvePointSave() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curvePointService.curvePointSave(curvePoint);

        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void testCurvePointSaveException() {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.save(curvePoint)).thenThrow(new RuntimeException());

        assertThrows(CurvePointSaveException.class, () -> curvePointService.curvePointSave(curvePoint));
    }

    @Test
    void testCurvePointFindById() throws CurvePointFindByIdException {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        curvePoint.setTerm(10d);
        curvePoint.setValue(20d);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint response = curvePointService.curvePointFindById(1);

        assertEquals(1, (byte) response.getCurveId());
    }

    @Test
    void testCurvePointFindById_CurvePointNotFoundException() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(CurvePointFindByIdException.class, () -> curvePointService.curvePointFindById(1));
        assertEquals(CurvePointNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testCurvePointUpdateOverloadWithIdVerification_Success() throws CurvePointUpdateException {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curvePointService.curvePointUpdate(1, curvePoint);

        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void testCurvePointUpdateOverloadWithIdVerification_Failed() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        Exception exception = assertThrows(CurvePointUpdateException.class, () -> curvePointService.curvePointUpdate(2, curvePoint));

        assertEquals(CurvePointIncoherenceBetweenObjectException.class, exception.getCause().getClass());

        verify(curvePointRepository, never()).save(curvePoint);
    }

    @Test
    void testCurvePointUpdateException() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        when(curvePointRepository.save(curvePoint)).thenThrow(new RuntimeException());

        assertThrows(CurvePointUpdateException.class, () -> curvePointService.curvePointUpdate(1, curvePoint));
    }

    @Test
    void testCurvePointDelete() throws CurvePointDeleteException {
        doNothing().when(curvePointRepository).deleteById(1);
        curvePointService.curvePointDelete(1);
        verify(curvePointRepository).deleteById(1);
    }

    @Test
    void testCurvePointDeleteException() {
        doThrow(new RuntimeException()).when(curvePointRepository).deleteById(anyInt());
        assertThrows(CurvePointDeleteException.class, () -> curvePointService.curvePointDelete(1));
    }
}
