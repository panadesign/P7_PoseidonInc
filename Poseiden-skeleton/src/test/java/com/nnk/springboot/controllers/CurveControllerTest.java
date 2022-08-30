package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(CurveController.class)
class CurveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private CrudService<CurvePoint> curvePointCrudService;

	@MockBean
	private CurvePointRepository curvePointRepository;

	@MockBean
	private UserRepository userRepository;


	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void getCurvePointList() throws Exception {
		List<CurvePoint> curvePointList = List.of(
				new CurvePoint(12, 12d, 12d)
		);


		when(curvePointCrudService.getAll()).thenReturn(curvePointList);
		mockMvc.perform(get("/curvePoint/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"));
	}

	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void getCurvePointForm() throws Exception {
		mockMvc.perform(get("/curvePoint/add")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"));
	}


	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void validate() {
	}

	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void showUpdateForm() throws Exception {
		CurvePoint curvePoint = new CurvePoint(1, 2, 21d, 33d);
		when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
		when(curvePointCrudService.getById(curvePoint.getId())).thenReturn(curvePoint);

		int id = curvePoint.getId();

		mockMvc.perform(get("/curvePoint/update/{id}", id))
				.andExpect(status().isOk());
	}

	@Test
	void updateCurvePoint() {
	}

	@Test
	void deleteBid() {
	}
}