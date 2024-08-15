package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointResponse;
import com.nnk.springboot.dto.CurvePointResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.CurvePointServiceException.*;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Test
    void testCurvePointAggregationInfo() throws CurvePointAggregationInfoException {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        curvePoint.setTerm(10d);
        curvePoint.setValue(20d);

        List<CurvePoint> curvePoints = List.of(curvePoint);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        CurvePointResponse response = curvePointService.curvePointAggregationInfo();

        List<CurvePointResponseAggregationInfoDTO> dtos = response.getCurvePointResponseAggregationInfoDTO();

        assertEquals("1", dtos.getFirst().getCurveId());
        assertEquals("10.0", dtos.getFirst().getTerm());
        assertEquals("20.0", dtos.getFirst().getValue());
    }

    @Test
    void testCurvePointAggregationInfo_Failed() {
        when(curvePointRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(CurvePointAggregationInfoException.class, () -> curvePointService.curvePointAggregationInfo());
    }

    @Test
    void testCurvePointSave_Success() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint savedCurvePoint = curvePointService.curvePointSave(curvePoint);

        assertEquals(curvePoint, savedCurvePoint);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void testCurvePointSave_Exception() {
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

        assertEquals((byte) 1, response.getCurveId());
        assertEquals(10d, response.getTerm());
        assertEquals(20d, response.getValue());
    }

    @Test
    void testCurvePointFindById_Exception() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CurvePointFindByIdException.class, () -> curvePointService.curvePointFindById(1));
    }

    @Test
    void testCurvePointUpdate_Success() throws CurvePointUpdateException {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        curvePointService.curvePointUpdate(1, curvePoint);

        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void testCurvePointUpdate_IDMismatch() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        Exception exception = assertThrows(CurvePointUpdateException.class, () -> curvePointService.curvePointUpdate(2, curvePoint));
        assertEquals(CurvePointIncoherenceBetweenObjectException.class, exception.getCause().getClass());
    }

    @Test
    void testCurvePointUpdate_Exception() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        when(curvePointRepository.save(curvePoint)).thenThrow(new RuntimeException());

        assertThrows(CurvePointUpdateException.class, () -> curvePointService.curvePointUpdate(1, curvePoint));
    }

    @Test
    void testCurvePointDelete() throws CurvePointDeleteException {
        doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.curvePointDelete(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }

    @Test
    void testCurvePointDelete_Exception() {
        doThrow(new RuntimeException()).when(curvePointRepository).deleteById(1);

        assertThrows(CurvePointDeleteException.class, () -> curvePointService.curvePointDelete(1));
    }
}
