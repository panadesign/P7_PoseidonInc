package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidListServiceImplTest {

	private BidListService bidListService;

	@Mock
	private BidListRepository bidListRepository;

	@BeforeEach
	void init() {
		bidListService = new BidListServiceImpl(bidListRepository);
	}

	@Test
	void addBidList() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);

		//WHEN
		when(bidListRepository.save(any())).thenAnswer(b -> b.getArguments()[0]);

		BidList newBidList = bidListService.addBidList(bidList);
		//THEN
		assertThat(newBidList)
				.satisfies(bl -> {
					assertThat(bl.getBidListId()).hasToString("2");
					assertThat(bl.getAccount()).hasToString("account1");
					assertThat(bl.getType()).hasToString("type1");
					assertThat(bl.getBidQuantity()).hasToString("12.0");
				});
	}

	@Test
	void addBidListAlreadyExistException() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);

		//WHEN
		when(bidListRepository.findById(2)).thenReturn(Optional.of(bidList));

		//THEN
		Assertions.assertThrows(ResourceExistException.class, () -> bidListService.addBidList(bidList));

	}

	@Test
	void delete() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);


		//WHEN
		when(bidListRepository.findById(2)).thenReturn(Optional.of(bidList));
		bidListService.deleteBidList(bidList);

		//THEN
		verify(bidListRepository, times(1)).delete(bidList);
	}

	@Test
	void deleteException() {
		//GIVEN
		BidList bidList2 = new BidList(2, "account1", "type1", 12);

		//THEN
		Assertions.assertThrows(ResourceExistException.class, () -> bidListService.deleteBidList(bidList2));
	}

	@Test
	void getBidListById() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);

		//WHEN
		when(bidListRepository.findBidListById(2)).thenReturn(Optional.of(bidList));

		BidList bidList1 = bidListService.getBidListById(bidList);

		//THEN
		Assertions.assertEquals(2, bidList1.getBidListId());
	}

	@Test
	void getBidListByIdNotExistResource() {
		BidList bidList = new BidList(2, "account1", "type1", 12);

		Assertions.assertThrows(ResourceNotExistException.class, () -> bidListService.getBidListById(bidList));
	}

	@Test
	void getAllBidList() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);
		BidList bidList2 = new BidList(3, "account2", "type2", 129);

		List<BidList> allBidList = bidListService.getAllBidList();

		allBidList.add(bidList);
		allBidList.add(bidList2);
		when(bidListRepository.findAll()).thenReturn(allBidList);

		List<BidList> bidsList = bidListService.getAllBidList();

		Assertions.assertEquals(2, bidsList.size());
	}


}