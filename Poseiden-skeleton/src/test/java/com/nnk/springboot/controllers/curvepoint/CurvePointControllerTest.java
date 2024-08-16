package com.nnk.springboot.controllers.curvepoint;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointResponse;
import com.nnk.springboot.exceptions.CurvePointServiceException.*;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointControllerTest {

    @InjectMocks
    CurvePointController curvePointController;

    @Mock
    CurvePointService curvePointService;

    @Mock
    Model model;

    @Test
    void testHome() throws CurvePointAggregationInfoException {
        CurvePointResponse curvePointResponse = new CurvePointResponse();

        when(curvePointService.curvePointAggregationInfo()).thenReturn(curvePointResponse);

        String viewName = curvePointController.home(model);

        verify(curvePointService, times(1)).curvePointAggregationInfo();

        assertEquals("curvePoint/list", viewName);

        verify(model, times(1)).addAttribute(eq("curvePoints"), eq(curvePointResponse.getCurvePointResponseAggregationInfoDTO()));
    }

    @Test
    void testAddCurveForm() {
        CurvePoint curvePoint = new CurvePoint();
        String viewName = curvePointController.addCurveForm(curvePoint, model);

        assertEquals("curvePoint/add", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testValidate_True() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointService.curvePointSave(curvePoint)).thenReturn(curvePoint);

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("redirect:/home/curvePoint/list", viewName);
        verify(curvePointService, times(1)).curvePointSave(curvePoint);
        verify(model, never()).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testValidate_False() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        bindingResult.rejectValue("term", "error.curvePoint", "Term is required");

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));

        verify(curvePointService, never()).curvePointSave(curvePoint);
    }

    @Test
    void testFindCurvePointById() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);

        when(curvePointService.curvePointFindById(1)).thenReturn(curvePoint);

        String viewName = curvePointController.showUpdateForm(1, model);

        verify(curvePointService, times(1)).curvePointFindById(1);

        assertEquals("curvePoint/update", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testUpdateCurvePoint_True() throws CurvePointUpdateException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        doNothing().when(curvePointService).curvePointUpdate(1, curvePoint);

        String viewName = curvePointController.updateCurvePoint(1, curvePoint, bindingResult, model);

        assertEquals("redirect:/home/curvePoint/list", viewName);
        verify(curvePointService, times(1)).curvePointUpdate(1, curvePoint);
    }

    @Test
    void testUpdateCurvePoint_False() throws CurvePointUpdateException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        bindingResult.rejectValue("term", "error.curvePoint", "Term is required");

        String viewName = curvePointController.updateCurvePoint(1, curvePoint, bindingResult, model);

        assertEquals("curvePoint/update", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
        verify(curvePointService, never()).curvePointUpdate(1, curvePoint);
    }

    @Test
    void testDeleteCurvePoint() throws CurvePointDeleteException {
        doNothing().when(curvePointService).curvePointDelete(1);

        String viewName = curvePointController.deleteCurvePoint(1);

        verify(curvePointService, times(1)).curvePointDelete(1);
        assertEquals("redirect:/home/curvePoint/list", viewName);
    }
}
