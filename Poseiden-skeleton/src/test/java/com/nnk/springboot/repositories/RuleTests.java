package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RuleTests {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void ruleTest() {
        RuleName rule = new RuleName();
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQl");
        rule.setSqlPart("SQL Part");


        // Save
        rule = ruleNameRepository.save(rule);
        assertNotNull(rule.getId());
        assertEquals("Rule Name", rule.getName());

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        assertEquals("Rule Name Update", rule.getName());

        // Find
        List<RuleName> listResult = ruleNameRepository.findAll();
        assertFalse(listResult.isEmpty());

        // Delete
        Integer id = Integer.valueOf(rule.getId());
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        assertFalse(ruleList.isPresent());
    }
}
