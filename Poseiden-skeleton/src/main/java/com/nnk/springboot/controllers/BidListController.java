package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListsResponse;
import com.nnk.springboot.services.BidListService;
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

import static com.nnk.springboot.exceptions.BidListServiceException.*;

/**
 * Controller for managing BidList operations.
 */
@Controller
@RequestMapping("/home/bidList")
public class BidListController {

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    /**
     * Displays the list of bid lists.
     *
     * @param model the model to populate with bid lists
     * @return the view name for the bid list
     * @throws BidListAggregationInfoException if there is an error aggregating bid list info
     */
    @RequestMapping("/list")
    public String home(Model model) throws BidListAggregationInfoException {
        logger.info("Received request to list bid lists");
        BidListsResponse bidListsResponse = bidListService.bidListAggregationInfo();
        model.addAttribute("bidLists", bidListsResponse.getBidListsResponseAggregationInfoDTO());
        logger.info("Bid lists successfully retrieved and added to the model");
        return "bidList/list";
    }

    /**
     * Displays the form for adding a new bid list.
     *
     * @param bidList the bid list to add
     * @param model   the model to populate with the bid list
     * @return the view name for adding a bid list
     */
    @GetMapping("/add")
    public String addBidForm(BidList bidList, Model model) {
        logger.info("Received request to show add bid form");
        model.addAttribute("bidList", bidList);
        logger.info("Add bid form displayed");
        return "bidList/add";
    }

    /**
     * Validates and saves the bid list.
     *
     * @param bidList the bid list to save
     * @param result  the result of validation
     * @param model   the model to populate with the bid list
     * @return the view name for the add bid form or redirect to the list
     * @throws BidListSaveException if there is an error saving the bid list
     */
    @PostMapping("/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) throws BidListSaveException {
        logger.info("Received request to validate and save bid list");

        if (result.hasErrors()) {
            logger.info("Bid list validation failed");
            model.addAttribute("bidList", bidList);
            return "bidList/add";
        }

        bidListService.bidListSave(bidList);
        logger.info("Bid list successfully validated and saved");

        return "redirect:/home/bidList/list";
    }

    /**
     * Displays the form for updating an existing bid list.
     *
     * @param id    the ID of the bid list to update
     * @param model the model to populate with the bid list
     * @return the view name for updating the bid list
     * @throws BidListFindByIdException if there is an error finding the bid list by ID
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) throws BidListFindByIdException {
        logger.info("Received request to show update form for bid list with ID: {}", id);
        BidList bidList = bidListService.bidListFindById(id);
        model.addAttribute("bidList", bidList);
        logger.info("Update form for bid list with ID: {} displayed", id);
        return "bidList/update";
    }

    /**
     * Updates the specified bid list.
     *
     * @param id      the ID of the bid list to update
     * @param bidList the bid list data to update
     * @param result  the result of validation
     * @param model   the model to populate with the updated bid list
     * @return the redirect URL for the bid list
     * @throws BidListUpdateException if there is an error saving the updated bid list
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) throws BidListUpdateException {
        logger.info("Received request to update bid list with ID: {}", id);

        if (result.hasErrors()) {
            logger.info("Bid list update validation failed for ID: {}", id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        bidListService.bidListUpdate(id, bidList);
        logger.info("Bid list with ID: {} successfully updated", id);

        return "redirect:/home/bidList/list";
    }

    /**
     * Deletes the specified bid list.
     *
     * @param id the ID of the bid list to delete
     * @return the redirect URL for the bid list
     * @throws BidListDeleteException if there is an error deleting the bid list
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) throws BidListDeleteException {
        logger.info("Received request to delete bid list with ID: {}", id);
        bidListService.bidListDelete(id);
        logger.info("Bid list with ID: {} successfully deleted", id);
        return "redirect:/home/bidList/list";
    }
}
