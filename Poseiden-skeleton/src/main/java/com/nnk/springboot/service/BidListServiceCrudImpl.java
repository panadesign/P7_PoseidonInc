package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The type Bid list service crud.
 */
@Log4j2
@Component
@Qualifier("bidList")
public class BidListServiceCrudImpl extends AbstractServiceCrud<BidList, BidListRepository> {

    /**
     * Instantiates a new Bid list service crud.
     *
     * @param repository the repository
     */
    public BidListServiceCrudImpl(BidListRepository repository) {
		super(repository);
	}

	@Override
	public BidList add(BidList bidList) {
		log.debug("A new bid list has been created: " + bidList.getAccount());
		if(bidList.getBidQuantity()<= 0) {
			throw new IllegalArgumentException("Bid quantity must be positive");
		}
		return repository.save(bidList);
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