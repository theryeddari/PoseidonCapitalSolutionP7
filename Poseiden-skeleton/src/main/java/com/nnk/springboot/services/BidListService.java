package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListsResponse;
import com.nnk.springboot.dto.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.exceptions.BidListServiceException;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.BidListServiceException.*;
/**
 * Class who manage logic about operation of Bid list

 */
@Service
@Transactional
public class BidListService {

    private static final Logger logger = LogManager.getLogger(BidListService.class);

    final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Retrieves aggregated bid list information.
     *
     * @return BidListsResponse containing aggregated bid list information.
     * @throws BidListAggregationInfoException if there is an error retrieving the information.
     */
    public BidListsResponse bidListAggregationInfo() throws BidListAggregationInfoException {
        logger.info("Entering bidListAggregationInfo method.");
        try {
            List<BidList> bidLists = bidListRepository.findAll();
            List<BidListsResponseAggregationInfoDTO> bidListsResponseAggregationInfoDTO = bidLists.stream().map(bidList ->
                            new BidListsResponseAggregationInfoDTO(
                                    String.valueOf(bidList.getBidListId()),
                                    bidList.getAccount(),
                                    bidList.getType(),
                                    String.valueOf(bidList.getBidQuantity())))
                    .toList();
            logger.info("Exiting bidListAggregationInfo method successfully.");
            return new BidListsResponse(bidListsResponseAggregationInfoDTO);
        } catch (Exception e) {
            logger.error("Error in bidListAggregationInfo method.", e);
            throw new BidListAggregationInfoException(e);
        }
    }

    /**
     * Saves a bid list entity.
     *
     * @param bidList the bid list to save.
     * @return the saved bid list.
     * @throws BidListSaveException if there is an error saving the bid list.
     */
    public BidList bidListSave(BidList bidList) throws BidListSaveException {
        logger.info("Entering bidListSave method with bidList: {}", bidList);
        try {
            // Enregistrez la bid list en utilisant le repository
            bidList = bidListRepository.save(bidList);
            logger.info("Exiting bidListSave method successfully with saved bidList: {}", bidList);
            return bidList;
        } catch (Exception e) {
            logger.error("Error in bidListSave method.", e);
            throw new BidListSaveException(e);
        }
    }

    /**
     * Finds a bid list by its ID.
     *
     * @param id the ID of the bid list to find.
     * @return the found bid list.
     * @throws BidListFindByIdException if there is an error finding the bid list.
     */
    public BidList bidListFindById(int id) throws BidListFindByIdException {
        logger.info("Entering BidListFindById method with ID: {}", id);
        try {
            Optional<BidList> bidList = bidListRepository.findById(id);
            if (bidList.isEmpty()) {
                logger.warn("Bid list with ID: {} not found.", id);
                throw new BidListNotFoundException();
            }
            logger.info("Exiting BidListFindById method successfully with found bidList: {}", bidList.get());
            return bidList.get();
        } catch (Exception e) {
            logger.error("Error in BidListFindById method.", e);
            throw new BidListFindByIdException(e);
        }
    }

    /**
     * Update a bid list entity with a given ID.
     *
     * @param id            the ID to validate.
     * @param bidList       the bid list to save.
     * @throws BidListUpdateException if there is an error saving the bid list.
     */
    public void bidListUpdate(int id, BidList bidList) throws BidListUpdateException {
        logger.info("Entering bidListSave method with ID: {} and bidList: {}", id, bidList);
        try {
            if (id == bidList.getBidListId()) {
                BidList savedBidList = bidListSave(bidList);
                logger.info("Exiting bidListSave method successfully with saved bidList: {}", savedBidList);
            } else {
                logger.warn("Bid list ID: {} does not match the ID in bidList: {}", id, bidList.getBidListId());
                throw new BidListIncoherenceBetweenObjectException(); // Assurez-vous que cette exception est définie
            }
        } catch (Exception e) {
            logger.error("Error in bidListSave method.", e);
            throw new BidListServiceException.BidListUpdateException(e);
        }
    }

    /**
     * Deletes a bid list by its ID.
     *
     * @param id the ID of the bid list to delete.
     * @throws BidListDeleteException if there is an error deleting the bid list.
     */
    public void bidListDelete(int id) throws BidListDeleteException {
        logger.info("Entering bidListDelete method with ID: {}", id);
        try {
            bidListRepository.deleteById(id);
            logger.info("Exiting bidListDelete method successfully.");
        } catch (Exception e) {
            logger.error("Error in bidListDelete method.", e);
            throw new BidListDeleteException(e);
        }
    }
}
