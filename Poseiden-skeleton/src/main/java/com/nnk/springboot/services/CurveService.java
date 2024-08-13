package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.bidlist.CurvePointResponse;
import com.nnk.springboot.dto.bidlist.CurvePointResponseAggregationInfoDTO;

import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.CurvePointServiceException.*;

/**
 * Class who manage logic about operation of Bid list
 */
@Service
@Transactional
public class CurveService {

    private static final Logger logger = LoggerFactory.getLogger(CurveService.class);

    final CurvePointRepository curvePointRepository;

    public CurveService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Retrieves aggregated curve point information.
     *
     * @return CurvePointResponse containing aggregated curve point information.
     * @throws CurvePointAggregationInfoException if there is an error retrieving the information.
     */
    public CurvePointResponse curvePointAggregationInfo() throws CurvePointAggregationInfoException {
        logger.info("Entering curvePointAggregationInfo method.");
        try {
            List<CurvePoint> curvePoints = curvePointRepository.findAll();
            List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTO = curvePoints.stream().map(curvePoint ->
                            new CurvePointResponseAggregationInfoDTO(
                                    String.valueOf(curvePoint.getCurveId()),
                                    String.valueOf(curvePoint.getTerm()),
                                    String.valueOf(curvePoint.getValue())
                                    ))
                    .toList();
            logger.info("Exiting curvePointAggregationInfo method successfully.");
            return new CurvePointResponse(curvePointResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in curvePointAggregationInfo method.", e);
            throw new CurvePointAggregationInfoException(e);
        }
    }

    /**
     * Saves a curve point entity.
     *
     * @param curvePoint       the curve point to save.
     * @param bindingResult the result of binding validation.
     * @return the saved curve point.
     * @throws CurvePointSaveException if there is an error saving the curve point.
     */
    public CurvePoint curvePointSave(CurvePoint curvePoint, BindingResult bindingResult) throws CurvePointSaveException {
        logger.info("Entering curvePoint Save method with curvePoint: {}", curvePoint);
        try {
            if (!bindingResult.hasFieldErrors()) {
                curvePoint = curvePointRepository.save(curvePoint);
            }
            logger.info("Exiting curvePointSave method successfully with saved curvePoint: {}", curvePoint);
            return curvePoint;
        } catch (Exception e) {
            logger.error("Error in curvePointSave method.", e);
            throw new CurvePointSaveException(e);
        }
    }

    /**
     * Finds a curve point by its ID.
     *
     * @param id the ID of the curve point to find.
     * @return the found curve point.
     * @throws CurvePointFindByIdException if there is an error finding the curve point.
     */
    public CurvePoint CurvePointFindById(int id) throws CurvePointFindByIdException {
        logger.info("Entering CurvePointFindById method with ID: {}", id);
        try {
            Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
            if (curvePoint.isEmpty()) {
                logger.warn("Bid list with ID: {} not found.", id);
                throw new CurvePointNotFoundException();
            }
            logger.info("Exiting CurvePointFindById method successfully with found curvePoint: {}", curvePoint.get());
            return curvePoint.get();
        } catch (Exception e) {
            logger.error("Error in CurvePointFindById method.", e);
            throw new CurvePointFindByIdException(e);
        }
    }

    /**
     * Saves a curve point entity with a given ID.
     *
     * @param id            the ID to validate.
     * @param curvePoint       the curve point to save.
     * @param bindingResult the result of binding validation.
     * @return the saved curve point.
     * @throws CurvePointSaveException if there is an error saving the curve point.
     */
    public CurvePoint curvePointSave(int id, CurvePoint curvePoint, BindingResult bindingResult) throws CurvePointSaveException {
        logger.info("Entering curvePointSave method with ID: {} and curvePoint: {}", id, curvePoint);
        try {
            if (id == curvePoint.getCurveId()) {
                CurvePoint savedCurvePoint = curvePointSave(curvePoint, bindingResult);
                logger.info("Exiting curvePointSave method successfully with saved curvePoint: {}", savedCurvePoint);
                return savedCurvePoint;
            } else {
                logger.warn("Bid list ID: {} does not match the ID in curvePoint: {}", id, curvePoint.getCurveId());
                throw new CurvePointIncoherenceBetweenObject();
            }
        } catch (Exception e) {
            logger.error("Error in curvePointSave method.", e);
            throw new CurvePointSaveException(e);
        }
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete.
     * @throws CurvePointDeleteException if there is an error deleting the curve point.
     */
    public void curvePointDelete(int id) throws CurvePointDeleteException {
        logger.info("Entering curvePointDelete method with ID: {}", id);
        try {
            curvePointRepository.deleteById(id);
            logger.info("Exiting curvePointDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in curvePointDelete method.", e);
            throw new CurvePointDeleteException(e);
        }
    }

}
