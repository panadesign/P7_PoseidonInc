package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TradeServiceCrudImplTest {
	private TradeServiceCrudImpl tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@BeforeEach
	void init() {
		tradeService = new TradeServiceCrudImpl(tradeRepository);
	}

	@Test
	void addTrade() {
		//GIVEN
		Trade trade = new Trade(1, "account", "type");

		//WHEN
		when(tradeRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

		Trade tradeToRegister = tradeService.add(trade);

		//THEN
		assertThat(tradeToRegister)
				.satisfies(u -> {
					assertThat(u.getAccount()).hasToString("account");
					assertThat(u.getType()).hasToString("type");
				});
	}

	@Test
	void deleteTrade() {
		//GIVEN
		Trade trade = new Trade(1, "account", "type");

		//WHEN
		tradeService.delete(trade.getId());

		//THEN
		verify(tradeRepository, times(1)).deleteById(trade.getId());
	}

	@Test
	void getTradeById() {
		//GIVEN
		Trade trade = new Trade(1, "account", "type");

		//WHEN
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

		Trade tradeToFind = tradeService.getById(trade.getId());

		//THEN
		Assertions.assertEquals(1, tradeToFind.getId());
	}

	@Test
	void getTradeByIdNotExistException() {
		int tradeNotExisting = 99;
		Assertions.assertThrows(ResourceNotExistException.class, () -> tradeService.getById(tradeNotExisting));
	}

	@Test
	void getAllTrades() {
		//GIVEN
		List<Trade> trades = List.of(
				new Trade(1, "account", "type"),
				new Trade(2, "account2", "type2")
		);

		//WHEN
		when(tradeRepository.findAll()).thenReturn(trades);

		List<Trade> allTrade = tradeService.getAll();

		//THEN
		Assertions.assertEquals(allTrade, trades);
	}

	@Test
	void updateTrade() {
		//GIVEN
		Trade trade = new Trade(1, "account", "type");
		//WHEN
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

		Trade tradeDto = new Trade("account2", "type2");

		Trade tradeUpdated = tradeService.update(1, tradeDto);

		Assertions.assertEquals(1, tradeUpdated.getId());
		Assertions.assertEquals("account2", tradeUpdated.getAccount());
		Assertions.assertEquals("type2", tradeUpdated.getType());
	}


	@Test
	void updateTradeNotExistingException() {
		Trade tradeDto = new Trade("account", "type");
		Assertions.assertThrows(ResourceNotExistException.class, () -> tradeService.update(1, tradeDto));
	}
}