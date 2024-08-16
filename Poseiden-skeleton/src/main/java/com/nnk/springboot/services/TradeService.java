package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeResponse;
import com.nnk.springboot.dto.TradeResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.TradeServiceException;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class that manages logic about operations of Trade.
 */
@Service
@Transactional
public class TradeService {

    private static final Logger logger = LogManager.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Retrieves aggregated trade information.
     *
     * @return TradeResponse containing aggregated trade information.
     * @throws TradeServiceException.TradeAggregationInfoException if there is an error retrieving the information.
     */
    public TradeResponse tradeAggregationInfo() throws TradeServiceException.TradeAggregationInfoException {
        logger.info("Entering tradeAggregationInfo method.");
        try {
            List<Trade> trades = tradeRepository.findAll();
            List<TradeResponseAggregationInfoDTO> tradeResponseAggregationInfoDTO = trades.stream()
                    .map(trade -> new TradeResponseAggregationInfoDTO(
                            String.valueOf(trade.getTradeId()),
                            trade.getAccount(),
                            trade.getType(),
                            String.valueOf(trade.getBuyQuantity())))
                    .toList();
            logger.info("Exiting tradeAggregationInfo method successfully.");
            return new TradeResponse(tradeResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in tradeAggregationInfo method.", e);
            throw new TradeServiceException.TradeAggregationInfoException(e);
        }
    }

    /**
     * Saves a trade entity.
     *
     * @param trade the trade to save.
     * @return the saved trade.
     * @throws TradeServiceException.TradeSaveException if there is an error saving the trade.
     */
    public Trade tradeSave(Trade trade) throws TradeServiceException.TradeSaveException {
        logger.info("Entering tradeSave method with trade: {}", trade);
        try {
            trade = tradeRepository.save(trade);
            logger.info("Exiting tradeSave method successfully with saved trade: {}", trade);
            return trade;
        } catch (Exception e) {
            logger.error("Error in tradeSave method.", e);
            throw new TradeServiceException.TradeSaveException(e);
        }
    }

    /**
     * Finds a trade by its ID.
     *
     * @param id the ID of the trade to find.
     * @return the found trade.
     * @throws TradeServiceException.TradeFindByIdException if there is an error finding the trade.
     */
    public Trade tradeFindById(int id) throws TradeServiceException.TradeFindByIdException {
        logger.info("Entering tradeFindById method with ID: {}", id);
        try {
            Optional<Trade> trade = tradeRepository.findById(id);
            if (trade.isEmpty()) {
                logger.warn("Trade with ID: {} not found.", id);
                throw new TradeServiceException.TradeNotFoundException();
            }
            logger.info("Exiting tradeFindById method successfully with found trade: {}", trade.get());
            return trade.get();
        } catch (Exception e) {
            logger.error("Error in tradeFindById method.", e);
            throw new TradeServiceException.TradeFindByIdException(e);
        }
    }

    /**
     * Updates a trade entity with a given ID.
     *
     * @param id the ID to validate.
     * @param trade the trade to update.
     * @throws TradeServiceException.TradeUpdateException if there is an error updating the trade.
     */
    public void tradeUpdate(int id, Trade trade) throws TradeServiceException.TradeUpdateException {
        logger.info("Entering tradeUpdate method with ID: {} and trade: {}", id, trade);
        try {
            if (id == trade.getTradeId()) {
                Trade updatedTrade = tradeSave(trade);
                logger.info("Exiting tradeUpdate method successfully with updated trade: {}", updatedTrade);
            } else {
                logger.warn("Trade ID: {} does not match the ID in trade: {}", id, trade.getTradeId());
                throw new TradeServiceException.TradeIncoherenceBetweenObjectException();
            }
        } catch (Exception e) {
            logger.error("Error in tradeUpdate method.", e);
            throw new TradeServiceException.TradeUpdateException(e);
        }
    }

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete.
     * @throws TradeServiceException.TradeDeleteException if there is an error deleting the trade.
     */
    public void tradeDelete(int id) throws TradeServiceException.TradeDeleteException {
        logger.info("Entering tradeDelete method with ID: {}", id);
        try {
            tradeRepository.deleteById(id);
            logger.info("Exiting tradeDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in tradeDelete method.", e);
            throw new TradeServiceException.TradeDeleteException(e);
        }
    }
}
