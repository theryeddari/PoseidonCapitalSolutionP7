package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListsResponse;
import com.nnk.springboot.dto.bidlist.BidListsResponseAggregationInfoDTO;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.exceptions.BidListServiceException.*;

@Service
public class BidListService {

    final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Transactional
    public BidListsResponse bidListAggregationInfo() throws BidListAggregationInfoException {
        try {
            List<BidList> bidLists = bidListRepository.findAll();
            List<BidListsResponseAggregationInfoDTO> bidListsResponseAggregationInfoDTO = bidLists.stream().map(bidList ->
                    new BidListsResponseAggregationInfoDTO(
                            String.valueOf(bidList.getBidListId()),
                            bidList.getAccount(),
                            bidList.getType(),
                            String.valueOf(bidList.getBidQuantity())))
                    .toList();
            return new BidListsResponse(bidListsResponseAggregationInfoDTO);
        } catch (Exception e) {
            throw new BidListAggregationInfoException(e);
        }
    }

    public BidList bidListSave(BidList bidList, BindingResult bindingResult) throws BidListSaveException {
        try{
            if(!bindingResult.hasFieldErrors()){
               bidList = bidListRepository.save(bidList);
            }
            return bidList;
        }catch (Exception e) {
            throw new BidListSaveException(e);
        }
    }

    public BidList BidListFindById(int id) throws FindBidListById {
        try{
            Optional<BidList> bidList = bidListRepository.findById(id);
            if(bidList.isEmpty()){
                throw new BidListNotFoundException();
            }
            return bidList.get();
        }catch (Exception e) {
            throw new FindBidListById(e);
        }
    }

    public BidList bidListSave(int id, BidList bidList, BindingResult bindingResult) throws BidListSaveException {
        try {
            if(id == bidList.getBidListId() ){
            return bidListSave(bidList,bindingResult);
        }else throw new BidListIncoherenceBetweenObject();

        } catch (Exception e) {
            throw new BidListSaveException(e);
        }
    }
}
