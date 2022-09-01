package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
class RatingControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private RatingRepository ratingRepository;

	private
	Rating rating;

	@BeforeEach
	public void init() {
		rating = new Rating("moody", "sand", "fitch", 1);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getRatingTest() throws Exception {
		//GIVEN
		ratingRepository.save(rating);

		//WHEN
		ResultActions response = mockMvc.perform(get("/rating/list")
				.contentType(MediaType.APPLICATION_JSON));

		// THEN
		response
				.andExpect(status().isOk())
				.andExpect(view().name("rating/list"))
				.andExpect(model().attribute("allRatings", List.of(rating)));
	}

	@Test
	@WithMockUser(authorities = "Admin")
	void getAddRatingForm() throws Exception {
		mockMvc.perform(get("/rating/add"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void validateAddRating() throws Exception {
		//WHEN
		ResultActions response = mockMvc.perform(post("/rating/validate")
				.with(csrf())
				.param("moodysRating", rating.getMoodysRating())
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@WithMockUser(authorities = "Admin")
	void getUpdateRatingForm() throws Exception {
		Rating ratingAdded = ratingRepository.save(rating);

		ResultActions response = mockMvc.perform(get("/rating/update/{id}", ratingAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void updateRating() throws Exception {
		Rating ratingAdded = ratingRepository.save(rating);

		ratingAdded.setFitchRating("TEST");

		ResultActions response = mockMvc.perform(post("/rating/update/{id}",ratingAdded.getId())
				.with(csrf())
				.param("moodysRating", rating.getMoodysRating())
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/rating/list"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void deleteRating() throws Exception {
		Rating ratingAdded = ratingRepository.save(rating);

		ResultActions response = mockMvc.perform(get("/rating/delete/{id}", ratingAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isFound())
				.andExpect(redirectedUrl("/rating/list"));
	}
}
