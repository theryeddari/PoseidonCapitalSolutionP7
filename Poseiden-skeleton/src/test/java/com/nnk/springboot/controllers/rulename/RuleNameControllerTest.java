package com.nnk.springboot.controllers.rulename;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameResponse;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static com.nnk.springboot.exceptions.RuleNameServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerTest {

    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @Test
    void testHome() throws RuleNameAggregationInfoException {
        RuleNameResponse ruleNameResponse = new RuleNameResponse();

        when(ruleNameService.ruleNameAggregationInfo()).thenReturn(ruleNameResponse);

        String viewName = ruleNameController.home(model);

        verify(ruleNameService, times(1)).ruleNameAggregationInfo();
        assertEquals("ruleName/list", viewName);
        verify(model, times(1)).addAttribute(eq("ruleNames"), eq(ruleNameResponse.getRuleNameResponseResponseAggregationInfoDTO()));
    }

    @Test
    void testAddRuleForm() {
        RuleName ruleName = new RuleName();

        String viewName = ruleNameController.addRuleForm(ruleName, model);

        assertEquals("ruleName/add", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));
    }

    @Test
    void testValidate_True() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = new BeanPropertyBindingResult(ruleName, "ruleName");

        when(ruleNameService.ruleNameSave(any(RuleName.class), eq(bindingResult))).thenReturn(ruleName);

        String viewName = ruleNameController.validate(ruleName, bindingResult, model);

        assertEquals("ruleName/add", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));
    }

    @Test
    void testValidate_False() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = new BeanPropertyBindingResult(ruleName, "ruleName");

        bindingResult.rejectValue("name", "error.ruleName", "Name is required");

        when(ruleNameService.ruleNameSave(any(RuleName.class), eq(bindingResult))).thenReturn(ruleName);

        ArgumentCaptor<BindingResult> bindingResultArgumentCaptor = ArgumentCaptor.forClass(BindingResult.class);
        ArgumentCaptor<RuleName> ruleNameArgumentCaptor = ArgumentCaptor.forClass(RuleName.class);

        String viewName = ruleNameController.validate(ruleName, bindingResult, model);

        assertEquals("ruleName/add", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));

        verify(ruleNameService).ruleNameSave(ruleNameArgumentCaptor.capture(), bindingResultArgumentCaptor.capture());
        assertEquals(ruleName, ruleNameArgumentCaptor.getValue());
        assertEquals(bindingResult, bindingResultArgumentCaptor.getValue());
    }

    @Test
    void testShowUpdateForm() throws RuleNameFindByIdException {
        RuleName ruleName = new RuleName();
        ruleName.setId((byte) 1);

        when(ruleNameService.ruleNameFindById(1)).thenReturn(ruleName);

        String viewName = ruleNameController.showUpdateForm(1, model);

        verify(ruleNameService, times(1)).ruleNameFindById(1);
        assertEquals("ruleName/update", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));
    }

    @Test
    void testUpdateRuleName_True() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = new BeanPropertyBindingResult(ruleName, "ruleName");

        when(ruleNameService.ruleNameSave(any(Integer.class), any(RuleName.class), eq(bindingResult))).thenReturn(ruleName);

        String viewName = ruleNameController.updateRuleName(1, ruleName, bindingResult, model);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));
    }

    @Test
    void testUpdateRuleName_False() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        BindingResult bindingResult = new BeanPropertyBindingResult(ruleName, "ruleName");

        bindingResult.rejectValue("name", "error.ruleName", "Name is required");

        when(ruleNameService.ruleNameSave(any(Integer.class), any(RuleName.class), eq(bindingResult))).thenReturn(ruleName);

        ArgumentCaptor<BindingResult> bindingResultArgumentCaptor = ArgumentCaptor.forClass(BindingResult.class);
        ArgumentCaptor<RuleName> ruleNameArgumentCaptor = ArgumentCaptor.forClass(RuleName.class);
        ArgumentCaptor<Integer> ruleNameIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        String viewName = ruleNameController.updateRuleName(1, ruleName, bindingResult, model);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(model, times(1)).addAttribute(eq("ruleName"), eq(ruleName));

        verify(ruleNameService).ruleNameSave(ruleNameIdArgumentCaptor.capture(), ruleNameArgumentCaptor.capture(), bindingResultArgumentCaptor.capture());
        assertEquals(ruleName, ruleNameArgumentCaptor.getValue());
        assertEquals(bindingResult, bindingResultArgumentCaptor.getValue());
        assertEquals(1, ruleNameIdArgumentCaptor.getValue());
    }

    @Test
    void testDeleteRuleName() throws RuleNameDeleteException {
        doNothing().when(ruleNameService).ruleNameDelete(1);

        String viewName = ruleNameController.deleteRuleName(1);

        verify(ruleNameService, times(1)).ruleNameDelete(1);
        assertEquals("redirect:/ruleName/list", viewName);
    }
}
