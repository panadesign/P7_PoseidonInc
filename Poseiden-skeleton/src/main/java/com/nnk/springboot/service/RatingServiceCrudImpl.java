package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * The type Rating service crud.
 */
@Log4j2
@Component
public class RatingServiceCrudImpl extends AbstractServiceCrud<Rating, RatingRepository>{

    /**
     * Instantiates a new Rating service crud.
     *
     * @param repository the repository
     */
    public RatingServiceCrudImpl(RatingRepository repository) {
		super(repository);
	}
	
	@Override
	public Rating add(Rating rating) {
		log.debug("A new rating has been created: " + rating.getId());
		return repository.save(rating);
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
