package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@Log4j2
public class RatingServiceImpl implements RatingService{

	private final RatingRepository ratingRepository;

	RatingServiceImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	public Rating addRating(Rating rating) {
		if(ratingRepository.findById(rating.getId()).isPresent()) {
			throw new ResourceExistException("This ratign is already existing: " + rating.getId());
		}

		Rating newRating = new Rating(rating.getId(), rating.getMoodysRating(), rating.getSandPRating(), rating.getFitchRating(), rating.getOrderNumber());
		log.debug("A new rating has been created: " + rating.getId());
		return ratingRepository.save(newRating);
	}

	public void deleteRating(Rating rating) {
		if(!ratingRepository.findById(rating.getId()).isPresent()){
			throw new ResourceExistException("This rating is not existing: " + rating.getId());
		}
		log.debug("Rating with id: " + rating.getId() + " is deleted");
		ratingRepository.delete(rating);
	}

	public List<Rating> getAllRating() {
		log.debug("All ratings: ");
		return ratingRepository.findAll();
	}

	public Rating getRatingById(Rating rating) {
		log.debug("Rating with id: " + rating.getId());
		return ratingRepository.findById(rating.getId()).orElseThrow(() -> new ResourceNotExistException("Rating with id " + rating.getId() + " doesn't exist."));
	}

}
