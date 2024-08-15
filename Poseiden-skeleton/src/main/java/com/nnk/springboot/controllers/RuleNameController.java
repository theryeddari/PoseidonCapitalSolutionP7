package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameResponse;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.nnk.springboot.exceptions.RuleNameServiceException.*;

/**
 * Controller for managing RuleName operations.
 */
@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Displays the list of rule names.
     *
     * @param model the model to populate with rule names
     * @return the view name for the rule name list
     * @throws RuleNameAggregationInfoException if there is an error aggregating rule name info
     */
    @RequestMapping("/home/ruleName/list")
    public String home(Model model) throws RuleNameAggregationInfoException {
        logger.info("Received request to list rule names");
        RuleNameResponse ruleNameResponse = ruleNameService.ruleNameAggregationInfo();
        model.addAttribute("ruleNames", ruleNameResponse.getRuleNameResponseAggregationInfoDTO());
        logger.info("Rule names successfully retrieved and added to the model");
        return "ruleName/list";
    }

    /**
     * Displays the form for adding a new rule name.
     *
     * @param ruleName the rule name to add
     * @param model    the model to populate with the rule name
     * @return the view name for adding a rule name
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName, Model model) {
        logger.info("Received request to show add rule name form");
        model.addAttribute("ruleName", ruleName);
        logger.info("Add rule name form displayed");
        return "ruleName/add";
    }

    /**
     * Validates and saves the rule name.
     *
     * @param ruleName the rule name to save
     * @param result   the result of validation
     * @param model    the model to populate with the rule name
     * @return the view name for the add rule name form or redirect to the list
     * @throws RuleNameSaveException if there is an error saving the rule name
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) throws RuleNameSaveException {
        logger.info("Received request to validate and save rule name");

        if (result.hasErrors()) {
            logger.info("Rule name validation failed");
            model.addAttribute("ruleName", ruleName);
            return "ruleName/add";
        }

        ruleNameService.ruleNameSave(ruleName);
        logger.info("Rule name successfully validated and saved");

        return "redirect:/home/ruleName/list";
    }

    /**
     * Displays the form for updating an existing rule name.
     *
     * @param id    the ID of the rule name to update
     * @param model the model to populate with the rule name
     * @return the view name for updating the rule name
     * @throws RuleNameFindByIdException if there is an error finding the rule name by ID
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws RuleNameFindByIdException {
        logger.info("Received request to show update form for rule name with ID: {}", id);
        RuleName ruleName = ruleNameService.ruleNameFindById(id);
        model.addAttribute("ruleName", ruleName);
        logger.info("Update form for rule name with ID: {} displayed", id);
        return "ruleName/update";
    }

    /**
     * Updates the specified rule name.
     *
     * @param id       the ID of the rule name to update
     * @param ruleName the rule name data to update
     * @param result   the result of validation
     * @param model    the model to populate with the updated rule name
     * @return the redirect URL for the rule name list
     * @throws RuleNameUpdateException if there is an error updating the rule name
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) throws RuleNameUpdateException {
        logger.info("Received request to update rule name with ID: {}", id);

        if (result.hasErrors()) {
            logger.info("Rule name update validation failed for ID: {}", id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }

        ruleNameService.ruleNameUpdate(id, ruleName);
        logger.info("Rule name with ID: {} successfully updated", id);

        return "redirect:/home/ruleName/list";
    }

    /**
     * Deletes the specified rule name.
     *
     * @param id the ID of the rule name to delete
     * @return the redirect URL for the rule name list
     * @throws RuleNameDeleteException if there is an error deleting the rule name
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) throws RuleNameDeleteException {
        logger.info("Received request to delete rule name with ID: {}", id);
        ruleNameService.ruleNameDelete(id);
        logger.info("Rule name with ID: {} successfully deleted", id);
        return "redirect:/home/ruleName/list";
    }
}
