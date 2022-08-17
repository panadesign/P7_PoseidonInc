package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RatingServiceCrudImpl extends AbstractServiceCrud<Rating, RatingRepository>{

	public RatingServiceCrudImpl(RatingRepository repository) {
		super(repository);
	}
	
	@Override
	public Rating add(Rating rating) {
		if(repository.findById(rating.getId()).isPresent()) {
			throw new ResourceExistException("This rating is already existing: " + rating.getId());
		}

		Rating newRating = new Rating(rating.getId(), rating.getMoodysRating(), rating.getSandPRating(), rating.getFitchRating(), rating.getOrderNumber());
		log.debug("A new rating has been created: " + rating.getId());
		return repository.save(newRating);
	}

	@Override
	public Rating update(Integer id, Rating ratingDto) {
		Rating ratingToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("Rating with id " + id + " doesn't exist."));

		Rating updatedRating = ratingToUpdate.update(ratingDto);
		repository.save(updatedRating);
		return updatedRating;
	}

}
