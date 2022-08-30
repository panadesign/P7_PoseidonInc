package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CrudService<Rating> ratingCrudService;

    @MockBean
    private RatingRepository ratingRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void getTrade() throws Exception {
        List<Rating> allRatings = List.of(
                new Rating("moody", "fdsf", "fdsf", 1)
        );

        when(ratingCrudService.getAll()).thenReturn(allRatings);
        mockMvc.perform(get("/rating/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showRatingAddForm() throws Exception {

        mockMvc.perform(get("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void validate() throws Exception {

    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void showUpdateForm() throws Exception {
        Rating rating = new Rating(1, "AA", "BB", "CCC", 2);
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(ratingCrudService.getById(rating.getId())).thenReturn(rating);

        int id = rating.getId();

        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void updateBid() {
    }

    @Test
    @WithMockUser(username = "admin", password = "AdminSpring_123")
    void deleteBid() throws Exception {
    }
}