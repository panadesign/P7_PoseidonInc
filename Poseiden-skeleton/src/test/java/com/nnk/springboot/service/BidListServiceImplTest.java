package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.ResourceNotExistException;
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
class 	BidListServiceImplTest {

	private BidListServiceCrudImpl bidListService;

	@Mock
	private BidListRepository bidListRepository;

	@BeforeEach
	void init() {
		bidListService = new BidListServiceCrudImpl(bidListRepository);
	}

	@Test
	void addBidList() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);

		//WHEN
		when(bidListRepository.save(any())).thenAnswer(b -> b.getArguments()[0]);

		BidList newBidList = bidListService.add(bidList);
		//THEN
		assertThat(newBidList)
				.satisfies(bl -> {
					assertThat(bl.getId()).hasToString("2");
					assertThat(bl.getAccount()).hasToString("account1");
					assertThat(bl.getType()).hasToString("type1");
					assertThat(bl.getBidQuantity()).hasToString("12.0");
				});
	}
	
	@Test
	void addBidListReturnIllegalArgumentException() {
		BidList bidList = new BidList(2, "account1", "type1", -12);
		Assertions.assertThrows(IllegalArgumentException.class,() -> bidListService.add( bidList));
	}

	@Test
	void delete() {
		//GIVEN
		BidList bidList = new BidList(3, "account1", "type1", 12);

		//WHEN
		bidListService.delete(bidList.getId());

		//THEN
		verify(bidListRepository, times(1)).deleteById(bidList.getId());
	}

	@Test
	void getBidListById() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);

		//WHEN
		when(bidListRepository.findById(2)).thenReturn(Optional.of(bidList));

		BidList bidList1 = bidListService.getById(bidList.getId());

		//THEN
		Assertions.assertEquals(2, bidList1.getId());
	}

	@Test
	void getBidListByIdNotExistResource() {
		int bidNotExisting = 99;
		Assertions.assertThrows(ResourceNotExistException.class, () -> bidListService.getById(bidNotExisting));
	}

	@Test
	void getAllBidList() {
		//GIVEN
		List<BidList> allBidList = List.of(
				new BidList(2, "account1", "type1", 12),
				new BidList(3, "account2", "type2", 129)
		);

		when(bidListRepository.findAll()).thenReturn(allBidList);

		List<BidList> bidsList = bidListService.getAll();

		Assertions.assertEquals(2, bidsList.size());
	}

	@Test
	void updateBidList() {
		//GIVEN
		BidList bidList = new BidList(2, "account1", "type1", 12);
		//WHEN
		when(bidListRepository.findById(2)).thenReturn(Optional.of(bidList));

		BidList bidListDto = new BidList("account4", "type9", 63);

		BidList bidListUpdated = bidListService.update(2, bidListDto);

		//THEN
		Assertions.assertEquals("account4", bidListUpdated.getAccount());
		Assertions.assertEquals("type9", bidListUpdated.getType());
		Assertions.assertEquals(63, bidListUpdated.getBidQuantity());
	}

	@Test
	void updateBidListNotExistingException() {
		BidList bidListDto = new BidList("account4", "type9", 63);
		Assertions.assertThrows(ResourceNotExistException.class, () -> bidListService.update(1, bidListDto));
	}

}