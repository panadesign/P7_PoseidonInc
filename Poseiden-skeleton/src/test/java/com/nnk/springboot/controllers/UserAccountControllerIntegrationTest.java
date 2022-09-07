package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
class UserAccountControllerIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	private UserAccount userAccount;

	@BeforeEach
	public void init() {
		userAccount = new UserAccount("userName", "Testtest!2022", "name", "USER");

		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getUserListByAdmin() throws Exception {
		//GIVEN
		UserAccount savedUser = userRepository.save(userAccount);

		//WHEN
		ResultActions response = mockMvc.perform(get("/user/list")
				.contentType(MediaType.APPLICATION_JSON));

		//THEN

		response.andExpect(status().isOk())
				.andExpect(view().name("user/list"))
				.andExpect(model().attribute("users", List.of(savedUser)));
	}

	@Test
	@WithMockUser(authorities = "USER")
	void getUserListByUser() throws Exception {
		//GIVEN
		userRepository.save(userAccount);

		//WHEN
		ResultActions response = mockMvc.perform(get("/user/list")
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getAddUserForm() throws Exception {
		mockMvc.perform(get("/user/add"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void validateAddUserAccount() throws Exception {
		//WHEN
		ResultActions response = mockMvc.perform(post("/user/validate")
				.with(csrf())
				.param("username", userAccount.getUsername())
				.param("password", userAccount.getPassword())
				.param("fullname", userAccount.getFullname())
				.param("role", userAccount.getRole())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		//THEN
		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/user/list"));
	}
	
	@Test
	@WithMockUser(authorities = "USER")
	void getAddUserFormWithUserRole() throws Exception {
		mockMvc.perform(get("/user/add"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void getUpdateUserAccountForm() throws Exception {
		UserAccount userAccountToAdd = new UserAccount("userName", "$2y$10$f80DJ24HiDgE1V2Rr4caLOwkL7tBGsPMMpY/Ehw63/3NhGVBLXiNO", "fullName", "USER");
		UserAccount userAccountAdded = userRepository.save(userAccountToAdd);

		ResultActions response = mockMvc.perform(get("/user/update/{id}", userAccountAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "USER")
	void getUpdateUserAccountFormWithUserRole() throws Exception {
		UserAccount userAccountToAdd = new UserAccount("userName", "$2y$10$f80DJ24HiDgE1V2Rr4caLOwkL7tBGsPMMpY/Ehw63/3NhGVBLXiNO", "fullName", "USER");
		UserAccount userAccountAdded = userRepository.save(userAccountToAdd);

		ResultActions response = mockMvc.perform(get("/user/update/{id}", userAccountAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void updateUserAccount() throws Exception {
        UserAccount userAccountAdded = userRepository.save(userAccount);

        userAccountAdded.setFullname("TEST");

        ResultActions response = mockMvc.perform(post("/user/update/{id}", userAccountAdded.getId())
				.with(csrf())
				.param("username", userAccount.getUsername())
				.param("password", userAccount.getPassword())
				.param("fullname", userAccount.getFullname())
				.param("role", userAccount.getRole())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/user/list"));

	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	void deleteUserAccount() throws Exception {
		UserAccount userAccountToAdd = new UserAccount("userName", "$2y$10$f80DJ24HiDgE1V2Rr4caLOwkL7tBGsPMMpY/Ehw63/3NhGVBLXiNO", "fullName", "USER");
		UserAccount userAccountAdded = userRepository.save(userAccountToAdd);

		ResultActions response = mockMvc.perform(get("/user/delete/{id}", userAccountAdded.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/user/list"));

	}
}
