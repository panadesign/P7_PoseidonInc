package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private BidListRepository bidListRepository;

	private
	BidList bid;


	@BeforeEach
	public void init() {
		bid = new BidList("Account", "Type", 12d);

		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getBidListTest() throws Exception {
		//GIVEN
		bidListRepository.save(bid);

		//WHEN
		ResultActions response = mockMvc.perform(get("/bidList/list")
				.contentType(MediaType.APPLICATION_JSON));

		// THEN
		response
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/list"))
				.andExpect(model().attribute("allBidList", List.of(bid)));
	}

	@Test
	@WithMockUser(authorities = "Admin")
	void getAddBidListForm() throws Exception {
		mockMvc.perform(get("/bidList/add"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void validateAddNewBidList() throws Exception {
		//WHEN
		ResultActions response = mockMvc.perform(post("/bidList/validate")
				.with(csrf())
				.param("account", bid.getAccount())
				.param("type", bid.getType())
				.param("bidQuantity", String.valueOf(bid.getBidQuantity()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/bidList/list"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void validateAddNewBidListWithError() throws Exception {
		//WHEN
		ResultActions response = mockMvc.perform(post("/bidList/validate")
				.with(csrf())
				.param("account", bid.getAccount())
				.param("type", "")
				.param("bidQuantity", String.valueOf(bid.getBidQuantity()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
	}

	@Test
	@WithMockUser(authorities = "Admin")
	void getUpdateBidListForm() throws Exception {
		BidList bidListAdded = bidListRepository.save(bid);

		ResultActions response = mockMvc.perform(get("/bidList/update/{id}", bidListAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void updateBid() throws Exception {
		BidList bidAdded = bidListRepository.save(bid);

		bidAdded.setBidQuantity(3d);

		ResultActions response = mockMvc.perform(post("/bidList/update/{id}",bidAdded.getId())
				.with(csrf())
				.param("account", bid.getAccount())
				.param("type", bid.getType())
				.param("bidQuantity", String.valueOf(bid.getBidQuantity()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/bidList/list"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void updateBidWithError() throws Exception {
		BidList bidAdded = bidListRepository.save(bid);

		bidAdded.setBidQuantity(3d);

		ResultActions response = mockMvc.perform(post("/bidList/update/{id}",bidAdded.getId())
				.with(csrf())
				.param("account", bid.getAccount())
				.param("type", "")
				.param("bidQuantity", String.valueOf(bid.getBidQuantity()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void deleteBid() throws Exception {
		BidList bidList = new BidList("Account", "Type", 5d);
		BidList bidListAdded = bidListRepository.save(bidList);

		ResultActions response = mockMvc.perform(get("/bidList/delete/{id}", bidListAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isFound())
				.andExpect(redirectedUrl("/bidList/list"));
	}
}
