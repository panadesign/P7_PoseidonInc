package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
	Rating addRating(Rating rating);
	void deleteRating(Rating rating);
	List<Rating> getAllRating();
	Rating getRatingById(Rating rating);
//	Rating updateRating(Rating rating);
}
