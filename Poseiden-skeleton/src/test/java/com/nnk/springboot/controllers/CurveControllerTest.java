package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.BidListServiceCrudImpl;
import com.nnk.springboot.service.CurvePointServiceCrudImpl;
import com.nnk.springboot.service.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurveController.class)
class CurveControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	CurvePointServiceCrudImpl curvePointServiceCrud;
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	@MockBean
	private CurveController curveController;

	private CurvePoint curvePoint;

	@BeforeEach
	void init() {
		curvePoint = new CurvePoint(12, 1d, 23d);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}
	@Test
	void home() {
	}

	@Test
	void addBidForm() throws Exception {
		mockMvc.perform(get("/curvePoint/add"))
				.andExpect(status().isOk());
	}

	@Test
	void validate() {
	}

	@Test
	void showUpdateForm() {
	}

	@Test
	void updateCurvePoint() {
	}

	@Test
	void deleteBid() {
	}
}