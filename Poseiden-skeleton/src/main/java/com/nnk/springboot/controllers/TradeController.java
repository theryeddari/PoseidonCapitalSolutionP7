package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.services.TradeService;
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

import static com.nnk.springboot.exceptions.TradeServiceException.*;

@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    /**
     * Displays the list of trades.
     *
     * @param model the model to populate with trades
     * @return the view name for the trade list
     */
    @RequestMapping("/trade/list")
    public String home(Model model) throws TradeAggregationInfoException {
        logger.info("Received request to list trades");
        TradeResponse tradeResponse = tradeService.tradeAggregationInfo();
        model.addAttribute("trades", tradeResponse.getTradeResponseAggregationInfoDTO());
        logger.info("Trades successfully retrieved and added to the model");
        return "trade/list";
    }

    /**
     * Displays the form for adding a new trade.
     *
     * @param trade the trade to add
     * @param model the model to populate with the trade
     * @return the view name for adding a trade
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade, Model model) {
        logger.info("Received request to show add trade form");
        model.addAttribute("trade", trade);
        logger.info("Add trade form displayed");
        return "trade/add";
    }

    /**
     * Validates and saves the trade.
     *
     * @param trade the trade to save
     * @param result the result of validation
     * @param model the model to populate with the trade
     * @return the view name for the add trade form or redirect to the list
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) throws TradeSaveException {
        logger.info("Received request to validate and save trade");
        if (result.hasErrors()) {
            logger.info("Trade validation failed");
            model.addAttribute("trade", trade);
            return "trade/add";
        }
        tradeService.tradeSave(trade);
        logger.info("Trade successfully validated and saved");
        return "redirect:/trade/list";
    }

    /**
     * Displays the form for updating an existing trade.
     *
     * @param id the ID of the trade to update
     * @param model the model to populate with the trade
     * @return the view name for updating the trade
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws TradeFindByIdException {
        logger.info("Received request to show update form for trade with ID: {}", id);
        Trade trade = tradeService.tradeFindById(id);
        model.addAttribute("trade", trade);
        logger.info("Update form for trade with ID: {} displayed", id);
        return "trade/update";
    }

    /**
     * Updates the specified trade.
     *
     * @param id the ID of the trade to update
     * @param trade the trade data to update
     * @param result the result of validation
     * @param model the model to populate with the updated trade
     * @return the redirect URL for the trade list
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) throws TradeSaveException {
        logger.info("Received request to update trade with ID: {}", id);
        if (result.hasErrors()) {
            logger.info("Trade validation failed");
            model.addAttribute("trade", trade);
            return "trade/update";
        }
        tradeService.tradeSave(id,trade);
        logger.info("Trade with ID: {} successfully updated", id);
        return "redirect:/trade/list";
    }

    /**
     * Deletes the specified trade.
     *
     * @param id the ID of the trade to delete
     * @return the redirect URL for the trade list
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) throws TradeDeleteException {
        logger.info("Received request to delete trade with ID: {}", id);
        tradeService.tradeDelete(id);
        logger.info("Trade with ID: {} successfully deleted", id);
        return "redirect:/trade/list";
    }
}
