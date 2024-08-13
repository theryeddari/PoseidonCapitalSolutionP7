package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.bidlist.CurvePointResponse;
import com.nnk.springboot.services.CurveService;
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
public class CurveController {

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    private CurveService curveService;

    /**
     * Displays the list of bid lists.
     *
     * @param model the model to populate with bid lists
     * @return the view name for the bid list
     * @throws CurvePointAggregationInfoException if there is an error aggregating bid list info
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) throws CurvePointAggregationInfoException {
        logger.info("Received request to list curve list");
        CurvePointResponse curvePointResponse = curveService.curvePointAggregationInfo();
        model.addAttribute("curvePoints", curvePointResponse.getCurvePointResponseAggregationInfoDTO());
        logger.info("curve point successfully retrieved and added to the model");
        return "curvePoint/list";
    }

    /**
     * Displays the form for adding a new bid list.
     *
     * @param curvePoint the curve point to add
     * @param model   the model to populate with the bid list
     * @return the view name for adding a bid list
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint, Model model) {
        logger.info("Received request to show add curve form");
        model.addAttribute("curvePoint", curvePoint);
        logger.info("Add curve form displayed");
        return "curvePoint/add";
    }

    /**
     * Validates and saves the bid list.
     *
     * @param curvePoint the curve point to save
     * @param result  the result of validation
     * @param model   the model to populate with the bid list
     * @return the view name for the add curve form or redirect to the list
     * @throws CurvePointSaveException if there is an error saving the bid list
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) throws CurvePointSaveException {
        logger.info("Received request to validate and save curve point");
        curvePoint = curveService.curvePointSave(curvePoint, result);
        model.addAttribute("curvePoint", curvePoint);
        logger.info("curve point successfully validated and saved");
        return "curvePoint/add";
    }

    /**
     * Displays the form for updating an existing bid list.
     *
     * @param id    the ID of the bid list to update
     * @param model the model to populate with the bid list
     * @return the view name for updating the bid list
     * @throws CurvePointFindByIdException if there is an error finding the bid list by ID
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) throws CurvePointFindByIdException {
        logger.info("Received request to show update form for curve point with ID: {}", id);
        CurvePoint curvePoint = curveService.CurvePointFindById(id);
        model.addAttribute("curvePoint", curvePoint);
        logger.info("Update form for curve point with ID: {} displayed", id);
        return "curvePoint/update";
    }
    /**
     * Updates the specified bid list.
     *
     * @param id      the ID of the curve point to update
     * @param curvePoint the curve point data to update
     * @param result  the result of validation
     * @param model   the model to populate with the updated curve point
     * @return the redirect URL for the curve point
     * @throws CurvePointSaveException if there is an error saving the updated bid list
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) throws CurvePointSaveException {
        logger.info("Received request to update curve point with ID: {}", id);
        curvePoint = curveService.curvePointSave(id, curvePoint, result);
        model.addAttribute("curvePoint", curvePoint);
        logger.info("Curve point with ID: {} successfully updated", id);
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes the specified bid list.
     *
     * @param id the ID of the curve point to delete
     * @return the redirect URL for the curve point
     * @throws CurvePointDeleteException if there is an error deleting the bid list
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id) throws CurvePointDeleteException {
        logger.info("Received request to delete curve point with ID: {}", id);
        curveService.curvePointDelete(id);
        logger.info("Curve point with ID: {} successfully deleted", id);
        return "redirect:/curvePoint/list";
    }
}
