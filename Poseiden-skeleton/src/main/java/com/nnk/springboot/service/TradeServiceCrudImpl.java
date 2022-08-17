package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TradeServiceCrudImpl extends AbstractServiceCrud<Trade, TradeRepository>{

	public TradeServiceCrudImpl(TradeRepository repository) {
		super(repository);
	}
	
	@Override
	public Trade add(Trade trade) {
		if(repository.findById(trade.getTradeId()).isPresent()){
			throw new ResourceExistException("This trade is already existing: " + trade.getTradeId());
		}
		Trade newTrade = new Trade(trade.getTradeId(), trade.getAccount(), trade.getType());
		log.debug("A new trade has been created: " + trade.getAccount());
		return repository.save(newTrade);
	}

	@Override
	public Trade update(Integer id, Trade tradeDto) {
		Trade tradeToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Trade with id " + id + " doesn't exist."));

		Trade updatedTrade = tradeToUpdate.update(tradeDto);
		repository.save(updatedTrade);
		return updatedTrade;
	}

}
