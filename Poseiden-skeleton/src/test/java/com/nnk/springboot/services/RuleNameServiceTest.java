package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameResponse;
import com.nnk.springboot.dto.RuleNameResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.RatingServiceException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.exceptions.RuleNameServiceException.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Test
    void testRuleNameAggregationInfo() throws RuleNameAggregationInfoException {
        RuleName ruleName = new RuleName();
        ruleName.setId((byte) 1);
        ruleName.setName("Rule1");
        ruleName.setDescription("Description1");
        ruleName.setJson("Json1");
        ruleName.setTemplate("Template1");
        ruleName.setSqlStr("SqlStr1");
        ruleName.setSqlPart("SqlPart1");

        List<RuleName> ruleNames = List.of(ruleName);
        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        RuleNameResponse response = ruleNameService.ruleNameAggregationInfo();

        List<RuleNameResponseAggregationInfoDTO> dtos = response.getRuleNameResponseAggregationInfoDTO();

        assertEquals("1", dtos.getFirst().getId());
        assertEquals("Rule1", dtos.getFirst().getName());
        assertEquals("Description1", dtos.getFirst().getDescription());
        assertEquals("Json1", dtos.getFirst().getJson());
        assertEquals("Template1", dtos.getFirst().getTemplate());
        assertEquals("SqlStr1", dtos.getFirst().getSqlStr());
        assertEquals("SqlPart1", dtos.getFirst().getSqlPart());
    }

    @Test
    void testRuleNameAggregationInfo_Failed() {
        when(ruleNameRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuleNameAggregationInfoException.class, () -> ruleNameService.ruleNameAggregationInfo());
    }

    @Test
    void testRuleNameSave_Success() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        ruleNameService.ruleNameSave(ruleName);

        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void testRuleNameSaveException() throws RuleNameSaveException {
        RuleName ruleName = new RuleName();
        when(ruleNameRepository.save(ruleName)).thenThrow(new RuntimeException());
        assertThrows(RuleNameSaveException.class, () -> ruleNameService.ruleNameSave(ruleName));
    }

    @Test
    void testRuleNameFindById() throws RuleNameFindByIdException {
        RuleName ruleName = new RuleName();
        ruleName.setId((byte) 1);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName response = ruleNameService.ruleNameFindById(1);

        assertEquals((byte) 1, response.getId());
    }

    @Test
    void testRuleNameFindById_Exception() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuleNameFindByIdException.class, () -> ruleNameService.ruleNameFindById(1));
    }

    @Test
    void testRuleNameDelete() throws RuleNameDeleteException {
        doNothing().when(ruleNameRepository).deleteById(1);
        ruleNameService.ruleNameDelete(1);
        verify(ruleNameRepository).deleteById(1);
    }

    @Test
    void testRuleNameDelete_Exception() {
        doThrow(new RuntimeException()).when(ruleNameRepository).deleteById(anyInt());
        assertThrows(RuleNameDeleteException.class, () -> ruleNameService.ruleNameDelete(1));
    }
}
