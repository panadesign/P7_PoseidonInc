package com.nnk.springboot.service;

import com.nnk.springboot.Exception.BidListNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@Log4j2
public class BidListServiceImpl implements BidListService{

	@Autowired
	BidListRepository bidListRepository;

	public BidList addBidList(BidList bidList) {
		BidList bidListToCreate = new BidList(bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
		log.debug("A new bid list has been created: " + bidListToCreate.getAccount());
		return bidListRepository.save(bidListToCreate);
	}

	public boolean delete(BidList bidList) {
		if(!bidListRepository.findById(bidList.getBidListId()).isPresent()){
			throw new BidListNotExistException("This bidlist is not existing: " + bidList.getBidListId());
		}
		log.debug("Bidlist with id: " + bidList.getBidListId() + " is deleted");
		bidListRepository.delete(bidList);
		return true;
	}

	public List<BidList> getAllBidList() {
		log.debug("All bidlist: " + bidListRepository.findAll());
		return bidListRepository.findAll();
	}
}
