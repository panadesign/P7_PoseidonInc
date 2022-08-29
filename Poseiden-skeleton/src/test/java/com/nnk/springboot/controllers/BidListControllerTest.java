package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceCrudImpl;
import com.nnk.springboot.service.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
class BidListControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	BidListServiceCrudImpl bidListServiceCrud;
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	@MockBean
	private BidListController bidListController;

	private BidList bid;

	@BeforeEach
	void init() {
		bid = new BidList("Account", "Type", 23d);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	void getBidList() throws Exception {
		when(bidListServiceCrud.getAll()).thenReturn(Collections.singletonList(bid));
		mockMvc.perform(get("/bidList/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/list"));
	}

	@Test
	void addBidForm() throws Exception {
		BidList bidList = new BidList("Account1", "Type1", 12d);

		when(bidListServiceCrud.add(bidList)).thenReturn(bidList);
		mockMvc.perform(get("/bidList/add")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void validate() throws Exception {
//		BidList bidList = new BidList("Account1", "Type1", 12d);
//		when(bidListServiceCrud.add(bidList)).thenReturn(bidList);
//
//		mockMvc.perform(post("/bidList/validate")
//						.sessionAttr("bidList", bidList)
//						.param("account", bidList.getAccount())
//						.param("type", bidList.getType())
//						.param("bidQuantity", bidList.getBidQuantity().toString()))
//				.andExpect(status().isCreated());
	}

	@Test
	void showUpdateForm() throws Exception {
//		BidList bidList = new BidList("Account1", "Type1", 12d);
//
//		when(bidListServiceCrud.update(bidList.getId(), bidList)).thenReturn(bidList);
//
//		mockMvc.perform(get("/bidList/update"))
//				.andExpect(status().isOk());
	}

	@Test
	void updateBid() {
	}

	@Test
	void deleteBid() {
	}
}