package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointResponse;
import com.nnk.springboot.dto.CurvePointResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.CurvePointServiceException;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class who manage logic about operation of Curve Points
 */
@Service
@Transactional
public class CurvePointService {

    private static final Logger logger = LoggerFactory.getLogger(CurvePointService.class);

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Retrieves aggregated curve point information.
     *
     * @return CurvePointResponse containing aggregated curve point information.
     * @throws CurvePointServiceException.CurvePointAggregationInfoException if there is an error retrieving the information.
     */
    public CurvePointResponse curvePointAggregationInfo() throws CurvePointServiceException.CurvePointAggregationInfoException {
        logger.info("Entering curvePointAggregationInfo method.");
        try {
            List<CurvePoint> curvePoints = curvePointRepository.findAll();
            List<CurvePointResponseAggregationInfoDTO> curvePointResponseAggregationInfoDTO = curvePoints.stream()
                    .map(curvePoint -> new CurvePointResponseAggregationInfoDTO(
                            String.valueOf(curvePoint.getCurveId()),
                            String.valueOf(curvePoint.getTerm()),
                            String.valueOf(curvePoint.getValue())))
                    .toList();
            logger.info("Exiting curvePointAggregationInfo method successfully.");
            return new CurvePointResponse(curvePointResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in curvePointAggregationInfo method.", e);
            throw new CurvePointServiceException.CurvePointAggregationInfoException(e);
        }
    }

    /**
     * Saves a curve point entity.
     *
     * @param curvePoint the curve point to save.
     * @return the saved curve point.
     * @throws CurvePointServiceException.CurvePointSaveException if there is an error saving the curve point.
     */
    public CurvePoint curvePointSave(CurvePoint curvePoint) throws CurvePointServiceException.CurvePointSaveException {
        logger.info("Entering curvePointSave method with curvePoint: {}", curvePoint);
        try {
            curvePoint = curvePointRepository.save(curvePoint);
            logger.info("Exiting curvePointSave method successfully with saved curvePoint: {}", curvePoint);
            return curvePoint;
        } catch (Exception e) {
            logger.error("Error in curvePointSave method.", e);
            throw new CurvePointServiceException.CurvePointSaveException(e);
        }
    }

    /**
     * Finds a curve point by its ID.
     *
     * @param id the ID of the curve point to find.
     * @return the found curve point.
     * @throws CurvePointServiceException.CurvePointFindByIdException if there is an error finding the curve point.
     */
    public CurvePoint curvePointFindById(int id) throws CurvePointServiceException.CurvePointFindByIdException {
        logger.info("Entering curvePointFindById method with ID: {}", id);
        try {
            Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
            if (curvePoint.isEmpty()) {
                logger.warn("Curve point with ID: {} not found.", id);
                throw new CurvePointServiceException.CurvePointNotFoundException();
            }
            logger.info("Exiting curvePointFindById method successfully with found curvePoint: {}", curvePoint.get());
            return curvePoint.get();
        } catch (Exception e) {
            logger.error("Error in curvePointFindById method.", e);
            throw new CurvePointServiceException.CurvePointFindByIdException(e);
        }
    }

    /**
     * Updates a curve point entity with a given ID.
     *
     * @param id         the ID to validate.
     * @param curvePoint the curve point to update.
     * @throws CurvePointServiceException.CurvePointUpdateException if there is an error updating the curve point.
     */
    public void curvePointUpdate(int id, CurvePoint curvePoint) throws CurvePointServiceException.CurvePointUpdateException {
        logger.info("Entering curvePointUpdate method with ID: {} and curvePoint: {}", id, curvePoint);
        try {
            if (id == curvePoint.getCurveId()) {
                CurvePoint updatedCurvePoint = curvePointSave(curvePoint);
                logger.info("Exiting curvePointUpdate method successfully with updated curvePoint: {}", updatedCurvePoint);
            } else {
                logger.warn("Curve point ID: {} does not match the ID in curvePoint: {}", id, curvePoint.getCurveId());
                throw new CurvePointServiceException.CurvePointIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in curvePointUpdate method.", e);
            throw new CurvePointServiceException.CurvePointUpdateException(e);
        }
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete.
     * @throws CurvePointServiceException.CurvePointDeleteException if there is an error deleting the curve point.
     */
    public void curvePointDelete(int id) throws CurvePointServiceException.CurvePointDeleteException {
        logger.info("Entering curvePointDelete method with ID: {}", id);
        try {
            curvePointRepository.deleteById(id);
            logger.info("Exiting curvePointDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in curvePointDelete method.", e);
            throw new CurvePointServiceException.CurvePointDeleteException(e);
        }
    }
}
