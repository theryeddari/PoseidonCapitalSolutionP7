package com.nnk.springboot.controllers.curvepoint;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.bidlist.CurvePointResponse;
import com.nnk.springboot.exceptions.CurvePointServiceException;
import com.nnk.springboot.services.CurveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.nnk.springboot.exceptions.CurvePointServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension .class)
public class CurvePointControllerTest {

    @InjectMocks
    CurveController curveController;

    @Mock
    CurveService curvePointService;

    @Mock
    Model model;

    @Test
    void testHome() throws CurvePointServiceException.CurvePointAggregationInfoException {
        CurvePointResponse curvePointResponse = new CurvePointResponse();

        when(curvePointService.curvePointAggregationInfo()).thenReturn(curvePointResponse);

        String viewName = curveController.home(model);

        verify(curvePointService, times(1)).curvePointAggregationInfo();

        assertEquals("curvePoint/list", viewName);

        verify(model, times(1)).addAttribute(eq("curvePoints"), eq(curvePointResponse.getCurvePointResponseAggregationInfoDTO()));
    }

    @Test
    void testAddCurveForm(){
        CurvePoint curvePoint = new CurvePoint();
        String viewName = curveController.addCurveForm(curvePoint,model);

        assertEquals("curvePoint/add", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testValidate_True() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointService.curvePointSave(curvePoint,bindingResult)).thenReturn(curvePoint);

        String viewName = curveController.validate(curvePoint, bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));

    }

    @Test
    void testValidate_FalseTest() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        bindingResult.rejectValue("term", "error.curvePoint", "Account is required");


        when(curvePointService.curvePointSave(curvePoint,bindingResult)).thenReturn(curvePoint);

        ArgumentCaptor<BindingResult> bindingResultArgumentCaptor = ArgumentCaptor.forClass(BindingResult.class);
        ArgumentCaptor<CurvePoint> curvePointArgumentCaptor = ArgumentCaptor.forClass(CurvePoint.class);
        String viewName = curveController.validate(curvePoint, bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));

        verify(curvePointService).curvePointSave(curvePointArgumentCaptor.capture(),bindingResultArgumentCaptor.capture());
        assertEquals(curvePoint, curvePointArgumentCaptor.getValue());
        assertEquals(bindingResult, bindingResultArgumentCaptor.getValue());
    }

    @Test
    void testFindCurvePointByIdTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId((byte) 1);
        when(curvePointService.CurvePointFindById(1)).thenReturn(curvePoint);


        String viewName = curveController.showUpdateForm(1, model);

        verify(curvePointService, times(1)).CurvePointFindById(1);

        assertEquals("curvePoint/update", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testUpdateCurvePoint_True() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        when(curvePointService.curvePointSave(1,curvePoint,bindingResult)).thenReturn(curvePoint);

        String viewName = curveController.updateCurve(1, curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));
    }

    @Test
    void testUpdateCurvePoint_False() throws CurvePointSaveException {
        CurvePoint curvePoint = new CurvePoint();
        BindingResult bindingResult = new BeanPropertyBindingResult(curvePoint, "curvePoint");

        bindingResult.rejectValue("term", "error.curvePoint", "Account is required");


        when(curvePointService.curvePointSave(1, curvePoint,bindingResult)).thenReturn(curvePoint);

        ArgumentCaptor<BindingResult> bindingResultArgumentCaptor = ArgumentCaptor.forClass(BindingResult.class);
        ArgumentCaptor<CurvePoint> curvePointArgumentCaptor = ArgumentCaptor.forClass(CurvePoint.class);
        ArgumentCaptor<Integer> curvePointIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        String viewName = curveController.updateCurve(1, curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(model, times(1)).addAttribute(eq("curvePoint"), eq(curvePoint));

        verify(curvePointService).curvePointSave(curvePointIdArgumentCaptor.capture(), curvePointArgumentCaptor.capture(),bindingResultArgumentCaptor.capture());
        assertEquals(curvePoint, curvePointArgumentCaptor.getValue());
        assertEquals(bindingResult, bindingResultArgumentCaptor.getValue());
        assertEquals(1, curvePointIdArgumentCaptor.getValue());
    }

    @Test
    void testDeleteCurvePoint() throws CurvePointDeleteException {
        doNothing().when(curvePointService).curvePointDelete(1);

        String viewName = curveController.deleteCurve(1);

        verify(curvePointService, times(1)).curvePointDelete(1);
        assertEquals("redirect:/curvePoint/list", viewName);
    }

}
