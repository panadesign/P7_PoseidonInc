package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@Log4j2
public class BidListServiceImpl implements BidListService{

	private final BidListRepository bidListRepository;

	BidListServiceImpl(BidListRepository bidListRepository) {
		this.bidListRepository = bidListRepository;
	}

	public BidList addBidList(BidList bidList) {
		if(bidListRepository.findById(bidList.getBidListId()).isPresent()){
			throw new ResourceExistException("This bid list is already existing: " + bidList.getBidListId());
		}
		BidList newBidList = new BidList(bidList.getBidListId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
		log.debug("A new bid list has been created: " + newBidList.getAccount());
		return bidListRepository.save(newBidList);
	}

	public void deleteBidList(BidList bidList) {
		if(!bidListRepository.findById(bidList.getBidListId()).isPresent()){
			throw new ResourceExistException("This bidlist is not existing: " + bidList.getBidListId());
		}
		log.debug("Bid list with id: " + bidList.getBidListId() + " is deleted");
		bidListRepository.delete(bidList);
	}

	public List<BidList> getAllBidList() {
		log.debug("All bid list: ");
		return bidListRepository.findAll();
	}

	public BidList getBidListById(BidList bidList) {
		log.debug("Bid list with id: " + bidList.getBidListId());
		return bidListRepository.findBidListById(bidList.getBidListId()).orElseThrow(() -> new ResourceNotExistException("Bid list with id " + bidList.getBidListId() + " doesn't exist."));
	}

//	public BidList updateBidList(BidList bidList) {
//
//		if(!bidListRepository.findById(bidList.getBidListId()).isPresent()) {
//			throw new ResourceExistException("This bidlist id is not existing: " + bidList.getBidListId());
//		}
//
//		BidList bidListToUpdate = bidList;
//		bidListToUpdate.setType(bidList.getType());
//		bidListToUpdate.setAccount(bidList.getAccount());
//		bidListToUpdate.setBidQuantity(bidList.getBidQuantity());
//		bidListToUpdate.setAskQuantity(bidListToUpdate.getAskQuantity());
//
//
//		return bidListRepository.save(bidListToUpdate);
//	}
}
