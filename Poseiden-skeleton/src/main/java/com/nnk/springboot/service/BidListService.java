package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {
	BidList addBidList(BidList bidList);
	boolean delete(BidList bidList);
	List<BidList> getAllBidList();
}
