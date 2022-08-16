package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {
	BidList addBidList(BidList bidList);
	void deleteBidList(BidList bidList);
	List<BidList> getAllBidList();
	BidList getBidListById(BidList bidList);
//	BidList updateBidList(BidList bidList);
}
