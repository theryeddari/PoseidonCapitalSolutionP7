package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointResponse;
import com.nnk.springboot.services.CurvePointService;
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

import static com.nnk.springboot.exceptions.CurvePointServiceException.*;

/**
 * Controller for managing CurvePoint operations.
 */
@Controller
public class CurvePointController {

    private static final Logger logger = LogManager.getLogger(CurvePointController.class);

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Displays the list of curve points.
     *
     * @param model the model to populate with curve points
     * @return the view name for the curve point list
     * @throws CurvePointAggregationInfoException if there is an error aggregating curve point info
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) throws CurvePointAggregationInfoException {
        logger.info("Received request to list curve points");
        CurvePointResponse curvePointResponse = curvePointService.curvePointAggregationInfo();
        model.addAttribute("curvePoints", curvePointResponse.getCurvePointResponseAggregationInfoDTO());
        logger.info("Curve points successfully retrieved and added to the model");
        return "curvePoint/list";
    }

    /**
     * Displays the form for adding a new curve point.
     *
     * @param curvePoint the curve point to add
     * @param model      the model to populate with the curve point
     * @return the view name for adding a curve point
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint, Model model) {
        logger.info("Received request to show add curve point form");
        model.addAttribute("curvePoint", curvePoint);
        logger.info("Add curve point form displayed");
        return "curvePoint/add";
    }

    /**
     * Validates and saves the curve point.
     *
     * @param curvePoint the curve point to save
     * @param result     the result of validation
     * @param model      the model to populate with the curve point
     * @return the view name for the add curve point form or redirect to the list
     * @throws CurvePointSaveException if there is an error saving the curve point
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) throws CurvePointSaveException {
        logger.info("Received request to validate and save curve point");

        if (result.hasErrors()) {
            logger.info("Curve point validation failed");
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/add";
        }

        curvePointService.curvePointSave(curvePoint);
        logger.info("Curve point successfully validated and saved");

        return "redirect:/curvePoint/list";
    }

    /**
     * Displays the form for updating an existing curve point.
     *
     * @param id    the ID of the curve point to update
     * @param model the model to populate with the curve point
     * @return the view name for updating the curve point
     * @throws CurvePointFindByIdException if there is an error finding the curve point by ID
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) throws CurvePointFindByIdException {
        logger.info("Received request to show update form for curve point with ID: {}", id);
        CurvePoint curvePoint = curvePointService.curvePointFindById(id);
        model.addAttribute("curvePoint", curvePoint);
        logger.info("Update form for curve point with ID: {} displayed", id);
        return "curvePoint/update";
    }

    /**
     * Updates the specified curve point.
     *
     * @param id         the ID of the curve point to update
     * @param curvePoint the curve point data to update
     * @param result     the result of validation
     * @param model      the model to populate with the updated curve point
     * @return the redirect URL for the curve point
     * @throws CurvePointUpdateException if there is an error updating the curve point
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) throws CurvePointUpdateException {
        logger.info("Received request to update curve point with ID: {}", id);

        if (result.hasErrors()) {
            logger.info("Curve point update validation failed for ID: {}", id);
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }

        curvePointService.curvePointUpdate(id, curvePoint);
        logger.info("Curve point with ID: {} successfully updated", id);

        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes the specified curve point.
     *
     * @param id the ID of the curve point to delete
     * @return the redirect URL for the curve point
     * @throws CurvePointDeleteException if there is an error deleting the curve point
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) throws CurvePointDeleteException {
        logger.info("Received request to delete curve point with ID: {}", id);
        curvePointService.curvePointDelete(id);
        logger.info("Curve point with ID: {} successfully deleted", id);
        return "redirect:/curvePoint/list";
    }
}
