package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceExistException;
import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

	private RatingService ratingService;

	@Mock
	private RatingRepository ratingRepository;

	@BeforeEach
	void init() {
		ratingService = new RatingServiceImpl(ratingRepository);
	}

	@Test
	void addRating() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		when(ratingRepository.save(rating)).thenAnswer(r -> r.getArguments()[0]);
		ratingService.addRating(rating);

		//THEN
		assertThat(rating)
				.satisfies(rating1 -> {
					assertThat(rating1.getId()).hasToString("1");
					assertThat(rating1.getMoodysRating()).hasToString("test");
					assertThat(rating1.getSandPRating()).hasToString("test1");
					assertThat(rating1.getFitchRating()).hasToString("test2");
					assertThat(rating1.getOrderNumber()).hasToString("3");
				});
	}

	@Test
	void addRatingAlreadyExistException() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		//THEN
		Assertions.assertThrows(ResourceExistException.class, () -> ratingService.addRating(rating));

	}

	@Test
	void deleteRating() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);


		//WHEN
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		ratingService.deleteRating(rating);

		//THEN
		verify(ratingRepository, times(1)).delete(rating);
	}

	@Test
	void deleteException() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//THEN
		Assertions.assertThrows(ResourceExistException.class, () -> ratingService.deleteRating(rating));
	}

	@Test
	void getAllRating() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);
		Rating rating2 = new Rating(2, "test", "test1", "test2", 3);

		List<Rating> allRatings = ratingService.getAllRating();

		allRatings.add(rating);
		allRatings.add(rating2);
		when(ratingRepository.findAll()).thenReturn(allRatings);

		List<Rating> ratingList = ratingService.getAllRating();

		Assertions.assertEquals(2, ratingList.size());
	}

	@Test
	void getRatingById() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		Rating getRating = ratingService.getRatingById(rating);

		//THEN
		Assertions.assertEquals(1, getRating.getId());
	}

	@Test
	void getBidListByIdNotExistResource() {
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		Assertions.assertThrows(ResourceNotExistException.class, () -> ratingService.getRatingById(rating));
	}

}