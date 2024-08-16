package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameResponse;
import com.nnk.springboot.dto.RuleNameResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.RuleNameServiceException;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class that manages logic about operations of Rule Name.
 */
@Service
@Transactional
public class RuleNameService {

    private static final Logger logger = LogManager.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Retrieves aggregated rule name information.
     *
     * @return RuleNameResponse containing aggregated rule name information.
     * @throws RuleNameServiceException.RuleNameAggregationInfoException if there is an error retrieving the information.
     */
    public RuleNameResponse ruleNameAggregationInfo() throws RuleNameServiceException.RuleNameAggregationInfoException {
        logger.info("Entering ruleNameAggregationInfo method.");
        try {
            List<RuleName> ruleNames = ruleNameRepository.findAll();
            List<RuleNameResponseAggregationInfoDTO> ruleNameResponseAggregationInfoDTO = ruleNames.stream()
                    .map(ruleName -> new RuleNameResponseAggregationInfoDTO(
                            String.valueOf(ruleName.getId()),
                            ruleName.getName(),
                            ruleName.getDescription(),
                            ruleName.getJson(),
                            ruleName.getTemplate(),
                            ruleName.getSqlStr(),
                            ruleName.getSqlPart()))
                    .toList();
            logger.info("Exiting ruleNameAggregationInfo method successfully.");
            return new RuleNameResponse(ruleNameResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in ruleNameAggregationInfo method.", e);
            throw new RuleNameServiceException.RuleNameAggregationInfoException(e);
        }
    }

    /**
     * Saves a rule name entity.
     *
     * @param ruleName the rule name to save.
     * @return the saved rule name.
     * @throws RuleNameServiceException.RuleNameSaveException if there is an error saving the rule name.
     */
    public RuleName ruleNameSave(RuleName ruleName) throws RuleNameServiceException.RuleNameSaveException {
        logger.info("Entering ruleNameSave method with ruleName: {}", ruleName);
        try {
            ruleName = ruleNameRepository.save(ruleName);
            logger.info("Exiting ruleNameSave method successfully with saved ruleName: {}", ruleName);
            return ruleName;
        } catch (Exception e) {
            logger.error("Error in ruleNameSave method.", e);
            throw new RuleNameServiceException.RuleNameSaveException(e);
        }
    }

    /**
     * Finds a rule name by its ID.
     *
     * @param id the ID of the rule name to find.
     * @return the found rule name.
     * @throws RuleNameServiceException.RuleNameFindByIdException if there is an error finding the rule name.
     */
    public RuleName ruleNameFindById(int id) throws RuleNameServiceException.RuleNameFindByIdException {
        logger.info("Entering ruleNameFindById method with ID: {}", id);
        try {
            Optional<RuleName> ruleName = ruleNameRepository.findById(id);
            if (ruleName.isEmpty()) {
                logger.warn("Rule name with ID: {} not found.", id);
                throw new RuleNameServiceException.RuleNameNotFoundException();
            }
            logger.info("Exiting ruleNameFindById method successfully with found ruleName: {}", ruleName.get());
            return ruleName.get();
        } catch (Exception e) {
            logger.error("Error in ruleNameFindById method.", e);
            throw new RuleNameServiceException.RuleNameFindByIdException(e);
        }
    }

    /**
     * Updates a rule name entity with a given ID.
     *
     * @param id the ID to validate.
     * @param ruleName the rule name to update.
     * @throws RuleNameServiceException.RuleNameUpdateException if there is an error updating the rule name.
     */
    public void ruleNameUpdate(int id, RuleName ruleName) throws RuleNameServiceException.RuleNameUpdateException {
        logger.info("Entering ruleNameUpdate method with ID: {} and ruleName: {}", id, ruleName);
        try {
            if (id == ruleName.getId()) {
                RuleName updatedRuleName = ruleNameSave(ruleName);
                logger.info("Exiting ruleNameUpdate method successfully with updated ruleName: {}", updatedRuleName);
            } else {
                logger.warn("Rule name ID: {} does not match the ID in ruleName: {}", id, ruleName.getId());
                throw new RuleNameServiceException.RuleNameIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in ruleNameUpdate method.", e);
            throw new RuleNameServiceException.RuleNameUpdateException(e);
        }
    }

    /**
     * Deletes a rule name by its ID.
     *
     * @param id the ID of the rule name to delete.
     * @throws RuleNameServiceException.RuleNameDeleteException if there is an error deleting the rule name.
     */
    public void ruleNameDelete(int id) throws RuleNameServiceException.RuleNameDeleteException {
        logger.info("Entering ruleNameDelete method with ID: {}", id);
        try {
            ruleNameRepository.deleteById(id);
            logger.info("Exiting ruleNameDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in ruleNameDelete method.", e);
            throw new RuleNameServiceException.RuleNameDeleteException(e);
        }
    }
}
