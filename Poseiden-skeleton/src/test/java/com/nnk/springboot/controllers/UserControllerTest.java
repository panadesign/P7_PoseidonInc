package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CrudService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
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
@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private CrudService<UserAccount> userAccountCrudService;
	
	@MockBean
	private UserRepository userRepository;
	
	
	@Test
	@WithMockUser(username = "user", password = "UserSpring_123")
	void getUserList() throws Exception {
		List<UserAccount> allUsers = List.of(
				new UserAccount("Account", "Testtest_2022", "name", "USER")
		);
		
		when(userAccountCrudService.getAll()).thenReturn(allUsers);
		mockMvc.perform(get("/user/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("user/list"));
	}
	
	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void addUserAddForm() throws Exception {
		mockMvc.perform(get("/user/add")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(view().name("user/add"));
	}
	
	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void validate() throws Exception {
//
	}
	
	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void showUpdateForm() throws Exception {
		UserAccount userAccount = new UserAccount(1, "username", "TestTest_2002", "name", "USER");
		when(userRepository.save(userAccount)).thenReturn(userAccount);
		when(userAccountCrudService.getById(userAccount.getId())).thenReturn(userAccount);
		
		int id = userAccount.getId();
		
		mockMvc.perform(get("/user/update/{id}", id))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateUserAccount() {
	}
	
	@Test
	@WithMockUser(username = "admin", password = "AdminSpring_123")
	void deleteUserAccount() throws Exception {
//
	
	}
}