package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CurvePointRepository curvePointRepository;

	private CurvePoint curvePoint;


	@BeforeEach
	public void init() {
		curvePoint = new CurvePoint(2, 12d, 3d);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getCurvePointTest() throws Exception {
		//GIVEN
		curvePointRepository.save(curvePoint);

		//WHEN
		ResultActions response = mockMvc.perform(get("/curvePoint/list")
				.contentType(MediaType.APPLICATION_JSON));

		// THEN
		response
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"))
				.andExpect(model().attribute("curvePointList", List.of(curvePoint)));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getAddCurvePointForm() throws Exception {
		mockMvc.perform(get("/curvePoint/add"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void validateAddNewCurvePoint() throws Exception {
		//WHEN
		ResultActions response = mockMvc.perform(post("/curvePoint/validate")
				.with(csrf())
				.param("curveId", String.valueOf(curvePoint.getCurveId()))
				.param("term", String.valueOf(curvePoint.getTerm()))
				.param("curveValue", String.valueOf(curvePoint.getCurveValue()))
				.accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/curvePoint/list"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getUpdateCurvePointForm() throws Exception {
		//GIVEN
		CurvePoint curvePointAdded = curvePointRepository.save(curvePoint);

		//WHEN
		ResultActions response = mockMvc.perform(get("/curvePoint/update/{id}", curvePointAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void updateCurvePoint() throws Exception {
		CurvePoint curvePointAdded = curvePointRepository.save(curvePoint);

		curvePointAdded.setTerm(3d);

		ResultActions response = mockMvc.perform(post("/curvePoint/update/{id}",curvePointAdded.getId())
				.with(csrf())
				.param("curveId", String.valueOf(curvePoint.getCurveId()))
				.param("term", String.valueOf(curvePoint.getTerm()))
				.param("curveValue", String.valueOf(curvePoint.getCurveValue()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/curvePoint/list"));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getDeleteCurvePoint() throws Exception {
		//GIVEN
		CurvePoint curvePointAdded = curvePointRepository.save(curvePoint);

		//WHEN
		ResultActions response = mockMvc.perform(get("/curvePoint/delete/{id}", curvePointAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().isFound())
				.andExpect(redirectedUrl("/curvePoint/list"));
	}
}
