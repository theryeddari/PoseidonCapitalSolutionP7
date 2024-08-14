package com.nnk.springboot.controllers.rulename;

import com.nnk.springboot.controllers.RuleNameControllerAdvice;
import com.nnk.springboot.exceptions.RuleNameServiceException.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.nnk.springboot.constants.ConstantsExceptions.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerAdviceTest {

    RuleNameControllerAdvice ruleNameControllerAdvice = new RuleNameControllerAdvice();

    @Test
    void handleRuleNameAggregationInfoException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = ruleNameControllerAdvice.handleRuleNameAggregationInfoException(new RuleNameAggregationInfoException(runtimeException));

        Assertions.assertTrue(result.contains(RULE_NAME_AGGREGATION_INFO_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRuleNameSaveException() {
        RuntimeException runtimeException = new RuntimeException();
        String result = ruleNameControllerAdvice.handleRuleNameSaveException(new RuleNameSaveException(runtimeException));

        Assertions.assertTrue(result.contains(RULE_NAME_SAVE_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRuleNameFindByIdException() {
        String result = ruleNameControllerAdvice.handleRuleNameFindByIdException(new RuleNameFindByIdException(new RuntimeException()));

        Assertions.assertTrue(result.contains(RULE_NAME_FIND_BY_ID_EXCEPTION + MORE_INFO));
    }

    @Test
    void handleRuleNameFindById_RuleNameNotFoundException() {
        RuleNameNotFoundException ruleNameNotFoundException = new RuleNameNotFoundException();
        String result = ruleNameControllerAdvice.handleRuleNameFindByIdException(new RuleNameFindByIdException(ruleNameNotFoundException));

        Assertions.assertTrue(result.contains(RULE_NAME_NOT_FOUND_EXCEPTION));
    }

    @Test
    void handleRuleNameUpdateException() {
        String result = ruleNameControllerAdvice.handleRuleNameUpdateException(new RuleNameUpdateException(new RuntimeException()));

        Assertions.assertTrue(result.contains(RULE_NAME_UPDATE_EXCEPTION));
    }

    @Test
    void handleRuleNameUpdateException_WithIdVerification_Failed() {
        RuleNameIncoherenceBetweenObjectException ruleNameIncoherenceBetweenObjectException = new RuleNameIncoherenceBetweenObjectException();
        String result = ruleNameControllerAdvice.handleRuleNameUpdateException(new RuleNameUpdateException(ruleNameIncoherenceBetweenObjectException));

        Assertions.assertTrue(result.contains(RULE_NAME_INCOHERENCE_BETWEEN_OBJECT_EXCEPTION));
    }

    @Test
    void handleRuleNameDeleteException() {
        RuleNameDeleteException ruleNameDeleteException = new RuleNameDeleteException(new RuntimeException());
        String result = ruleNameControllerAdvice.handleRuleNameDeleteException(ruleNameDeleteException);

        Assertions.assertTrue(result.contains(RULE_NAME_DELETE_EXCEPTION + MORE_INFO));
    }
}
