package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Qualifier("bidList")
public class BidListServiceCrudImpl extends AbstractServiceCrud<BidList, BidListRepository> {

	public BidListServiceCrudImpl(BidListRepository repository) {
		super(repository);
	}

	@Override
	public BidList add(BidList bidList) {
		if(repository.findById(bidList.getId()).isPresent()) {
			throw new ResourceExistException("This bid list is already existing: " + bidList.getId());
		}
		BidList newBidList = new BidList(bidList.getId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
		log.debug("A new bid list has been created: " + newBidList.getAccount());
		return repository.save(newBidList);
	}

	@Override
	public BidList update(Integer id, BidList bidListDto) {
		BidList bidListToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Bid list with id " + id + " doesn't exist."));

		BidList updatedBidList = bidListToUpdate.update(bidListDto);
		repository.save(updatedBidList);
		return updatedBidList;
	}

}
