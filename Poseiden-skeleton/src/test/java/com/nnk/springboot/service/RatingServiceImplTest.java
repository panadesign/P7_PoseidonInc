package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.ResourceExistException;
import com.nnk.springboot.exception.ResourceNotExistException;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

	private RatingServiceCrudImpl ratingService;

	@Mock
	private RatingRepository ratingRepository;

	@BeforeEach
	void init() {
		ratingService = new RatingServiceCrudImpl(ratingRepository);
	}

	@Test
	void addRating() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		when(ratingRepository.save(rating)).thenAnswer(r -> r.getArguments()[0]);
		ratingService.add(rating);

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
	void deleteRating() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		ratingService.delete(rating.getId());

		//THEN
		verify(ratingRepository, times(1)).deleteById(rating.getId());
	}

	@Test
	void getAllRating() {
		//GIVEN
		List<Rating> allRatings = List.of(
				new Rating(1, "test", "test1", "test2", 3),
				new Rating(2, "test", "test1", "test2", 3)
		);

		when(ratingRepository.findAll()).thenReturn(allRatings);

		List<Rating> ratingList = ratingService.getAll();

		Assertions.assertEquals(2, ratingList.size());
	}

	@Test
	void getRatingById() {
		//GIVEN
		Rating rating = new Rating(1, "test", "test1", "test2", 3);

		//WHEN
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		Rating getRating = ratingService.getById(rating.getId());

		//THEN
		Assertions.assertEquals(1, getRating.getId());
	}

	@Test
	void getRatingByIdNotExistResource() {
		int ratingNotExisting = 99;

		Assertions.assertThrows(ResourceNotExistException.class, () -> ratingService.getById(ratingNotExisting));
	}

	@Test
	void updateBidList() {
		//GIVEN
		Rating rating = new Rating(1, "mood", "sand", "fitch", 3);
		//WHEN
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		Rating ratingDto = new Rating("test", "test1", "test2", 4);

		Rating ratingUpdated = ratingService.update(1, ratingDto);

		//THEN
		Assertions.assertEquals("test", ratingUpdated.getMoodysRating());
		Assertions.assertEquals("test1", ratingUpdated.getSandPRating());
		Assertions.assertEquals("test2", ratingUpdated.getFitchRating());
		Assertions.assertEquals(4, ratingUpdated.getOrderNumber());
	}

	@Test
	void updateRatingNotExistingException() {
		Rating ratingDto = new Rating("test", "test1", "test2", 3);
		Assertions.assertThrows(ResourceNotExistException.class, () -> ratingService.update(1, ratingDto));
	}


}