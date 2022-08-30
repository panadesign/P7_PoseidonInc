package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import com.nnk.springboot.service.MyUserDetailsService;
import com.nnk.springboot.service.PrincipalUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(BidListController.class)
class BidListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CrudService<BidList> bidListServiceCrud;
	@MockBean
	private BidListRepository bidListRepository;
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PrincipalUser principalUser;

	@Test
	void getBidList() throws Exception {
//		BidList bid = new BidList("Account", "Type", 12d);
//		List<BidList> allBids = new ArrayList<>();
//		allBids.add(bid);
//		when(bidListRepository.findAll()).thenReturn(allBids);
//		mockMvc.perform(get("/bidList/list")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(view().name("bidList/list"));
	}

	@Test
	void addBidForm() throws Exception {
		UserAccount user = new UserAccount("bob", "dfd", "fds", "ADMIN");
		when(principalUser.getCurrentUserName()).thenReturn(user.getUsername());
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

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