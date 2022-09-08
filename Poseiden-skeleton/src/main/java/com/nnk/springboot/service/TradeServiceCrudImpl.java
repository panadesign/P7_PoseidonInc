package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * The type Trade service crud.
 */
@Log4j2
@Component
public class TradeServiceCrudImpl extends AbstractServiceCrud<Trade, TradeRepository>{

    /**
     * Instantiates a new Trade service crud.
     *
     * @param repository the repository
     */
    public TradeServiceCrudImpl(TradeRepository repository) {
		super(repository);
	}
	
	@Override
	public Trade add(Trade trade) {
		log.debug("A new trade has been created: " + trade.getAccount());
		return repository.save(trade);
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
