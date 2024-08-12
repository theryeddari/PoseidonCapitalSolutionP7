package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.dto.bidlist.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.BidListServiceException.*;

@Service
@Transactional
public class BidListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListService.class);

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
        LOGGER.info("Entering bidListAggregationInfo method.");
        try {
            List<BidList> bidLists = bidListRepository.findAll();
            List<BidListsResponseAggregationInfoDTO> bidListsResponseAggregationInfoDTO = bidLists.stream().map(bidList ->
                            new BidListsResponseAggregationInfoDTO(
                                    String.valueOf(bidList.getBidListId()),
                                    bidList.getAccount(),
                                    bidList.getType(),
                                    String.valueOf(bidList.getBidQuantity())))
                    .toList();
            LOGGER.info("Exiting bidListAggregationInfo method successfully.");
            return new BidListsResponse(bidListsResponseAggregationInfoDTO);
        } catch (Exception e) {
            LOGGER.error("Error in bidListAggregationInfo method.", e);
            throw new BidListAggregationInfoException(e);
        }
    }

    /**
     * Saves a bid list entity.
     *
     * @param bidList       the bid list to save.
     * @param bindingResult the result of binding validation.
     * @return the saved bid list.
     * @throws BidListSaveException if there is an error saving the bid list.
     */
    public BidList bidListSave(BidList bidList, BindingResult bindingResult) throws BidListSaveException {
        LOGGER.info("Entering bidListSave method with bidList: {}", bidList);
        try {
            if (!bindingResult.hasFieldErrors()) {
                bidList = bidListRepository.save(bidList);
            }
            LOGGER.info("Exiting bidListSave method successfully with saved bidList: {}", bidList);
            return bidList;
        } catch (Exception e) {
            LOGGER.error("Error in bidListSave method.", e);
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
    public BidList BidListFindById(int id) throws BidListFindByIdException {
        LOGGER.info("Entering BidListFindById method with ID: {}", id);
        try {
            Optional<BidList> bidList = bidListRepository.findById(id);
            if (bidList.isEmpty()) {
                LOGGER.warn("Bid list with ID: {} not found.", id);
                throw new BidListNotFoundException();
            }
            LOGGER.info("Exiting BidListFindById method successfully with found bidList: {}", bidList.get());
            return bidList.get();
        } catch (Exception e) {
            LOGGER.error("Error in BidListFindById method.", e);
            throw new BidListFindByIdException(e);
        }
    }

    /**
     * Saves a bid list entity with a given ID.
     *
     * @param id            the ID to validate.
     * @param bidList       the bid list to save.
     * @param bindingResult the result of binding validation.
     * @return the saved bid list.
     * @throws BidListSaveException if there is an error saving the bid list.
     */
    public BidList bidListSave(int id, BidList bidList, BindingResult bindingResult) throws BidListSaveException {
        LOGGER.info("Entering bidListSave method with ID: {} and bidList: {}", id, bidList);
        try {
            if (id == bidList.getBidListId()) {
                BidList savedBidList = bidListSave(bidList, bindingResult);
                LOGGER.info("Exiting bidListSave method successfully with saved bidList: {}", savedBidList);
                return savedBidList;
            } else {
                LOGGER.warn("Bid list ID: {} does not match the ID in bidList: {}", id, bidList.getBidListId());
                throw new BidListIncoherenceBetweenObject();
            }
        } catch (Exception e) {
            LOGGER.error("Error in bidListSave method.", e);
            throw new BidListSaveException(e);
        }
    }

    /**
     * Deletes a bid list by its ID.
     *
     * @param id the ID of the bid list to delete.
     * @throws BidListDeleteException if there is an error deleting the bid list.
     */
    public void bidListDelete(int id) throws BidListDeleteException {
        LOGGER.info("Entering bidListDelete method with ID: {}", id);
        try {
            bidListRepository.deleteById(id);
            LOGGER.info("Exiting bidListDelete method successfully.");
        } catch (Exception e) {
            LOGGER.error("Error in bidListDelete method.", e);
            throw new BidListDeleteException(e);
        }
    }
}
