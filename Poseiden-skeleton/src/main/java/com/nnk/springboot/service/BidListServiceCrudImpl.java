package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
public class BidListServiceCrudImpl extends AbstractServiceCrud<BidList, BidListRepository>{

	public BidListServiceCrudImpl(BidListRepository repository) {
		super(repository);
	}
	
	@Override
	public BidList add(BidList bidList) {
		if(repository.findById(bidList.getBidListId()).isPresent()){
			throw new ResourceExistException("This bid list is already existing: " + bidList.getBidListId());
		}
		BidList newBidList = new BidList(bidList.getBidListId(), bidList.getAccount(), bidList.getType(), bidList.getBidQuantity());
		log.debug("A new bid list has been created: " + newBidList.getAccount());
		return repository.save(newBidList);
	}

	@Override
	public BidList update(Integer id, BidList bidList) {
		return null;
	}

}
