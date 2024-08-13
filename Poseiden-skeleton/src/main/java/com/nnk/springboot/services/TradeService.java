package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.dto.TradeResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.TradeServiceException.*;

/**
 * Service class managing logic related to Trade operations.
 */
@Service
@Transactional
public class TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

    final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Retrieves aggregated trade information.
     *
     * @return TradeResponse containing aggregated trade information.
     * @throws TradeAggregationInfoException if there is an error retrieving the information.
     */
    public TradeResponse tradeAggregationInfo() throws TradeAggregationInfoException {
        logger.info("Entering tradeAggregationInfo method.");
        try {
            List<Trade> trades = tradeRepository.findAll();
            List<TradeResponseAggregationInfoDTO> tradeResponseAggregationInfoDTO = trades.stream().map(trade ->
                            new TradeResponseAggregationInfoDTO(
                                    String.valueOf(trade.getTradeId()),
                                    trade.getAccount(),
                                    trade.getType(),
                                    String.valueOf(trade.getBuyQuantity())))
                    .toList();
            logger.info("Exiting tradeAggregationInfo method successfully.");
            return new TradeResponse(tradeResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in tradeAggregationInfo method.", e);
            throw new TradeAggregationInfoException(e);
        }
    }

    /**
     * Saves a trade entity.
     *
     * @param trade          the trade to save.
     * @return the saved trade.
     * @throws TradeSaveException if there is an error saving the trade.
     */
    public Trade tradeSave(Trade trade) throws TradeSaveException {
        logger.info("Entering tradeSave method with trade: {}", trade);
        try {
            trade = tradeRepository.save(trade);
            logger.info("Exiting tradeSave method successfully with saved trade: {}", trade);
            return trade;
        } catch (Exception e) {
            logger.error("Error in tradeSave method.", e);
            throw new TradeSaveException(e);
        }
    }

    /**
     * Finds a trade by its ID.
     *
     * @param id the ID of the trade to find.
     * @return the found trade.
     * @throws TradeFindByIdException if there is an error finding the trade.
     */
    public Trade tradeFindById(int id) throws TradeFindByIdException {
        logger.info("Entering tradeFindById method with ID: {}", id);
        try {
            Optional<Trade> trade = tradeRepository.findById(id);
            if (trade.isEmpty()) {
                logger.warn("Trade with ID: {} not found.", id);
                throw new TradeNotFoundException();
            }
            logger.info("Exiting tradeFindById method successfully with found trade: {}", trade.get());
            return trade.get();
        } catch (Exception e) {
            logger.error("Error in tradeFindById method.", e);
            throw new TradeFindByIdException(e);
        }
    }

    /**
     * Saves a trade entity with a given ID.
     *
     * @param id             the ID to validate.
     * @param trade          the trade to save.
     * @return the saved trade.
     * @throws TradeSaveException if there is an error saving the trade.
     */
    public Trade tradeSave(int id, Trade trade) throws TradeSaveException {
        logger.info("Entering tradeSave method with ID: {} and trade: {}", id, trade);
        try {
            if (id == trade.getTradeId()) {
                Trade savedTrade = tradeSave(trade);
                logger.info("Exiting tradeSave method successfully with saved trade: {}", savedTrade);
                return savedTrade;
            } else {
                logger.warn("Trade ID: {} does not match the ID in trade: {}", id, trade.getTradeId());
                throw new TradeIncoherenceBetweenObject();
            }
        } catch (Exception e) {
            logger.error("Error in tradeSave method.", e);
            throw new TradeSaveException(e);
        }
    }

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete.
     * @throws TradeDeleteException if there is an error deleting the trade.
     */
    public void tradeDelete(int id) throws TradeDeleteException {
        logger.info("Entering tradeDelete method with ID: {}", id);
        try {
            tradeRepository.deleteById(id);
            logger.info("Exiting tradeDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in tradeDelete method.", e);
            throw new TradeDeleteException(e);
        }
    }
}
