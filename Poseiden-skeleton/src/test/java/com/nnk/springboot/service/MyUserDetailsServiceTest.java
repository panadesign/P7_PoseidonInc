package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {
	@InjectMocks
	private UserDetailsService userDetailsService = new MyUserDetailsService();
	@Mock
	UserRepository mockUserRepository;
	@Test
	void loadUserByUsername() {
		//GIVEN
		UserAccount userAccount = new UserAccount(12, "userName", "password", "fullName", "ADMIN");
		
		//WHEN
		when(mockUserRepository.findByUsername(userAccount.getUsername())).thenReturn(Optional.of(userAccount));
		UserAccount newUser = new UserAccount(userAccount.getUsername(), userAccount.getPassword(), userAccount.getFullname(), userAccount.getRole());
		
		userDetailsService.loadUserByUsername("userName");
		
		//THEN
		Assertions.assertEquals(newUser.getUsername(), userAccount.getUsername());
	}
	
	@Test
	void loadUserByUsernameReturnResourceNotExistingException() {
		//GIVEN
		UserAccount userAccount = new UserAccount("userName", "pass", "fullname", "role");
		
		//WHEN
		when(mockUserRepository.findByUsername(userAccount.getUsername())).thenReturn(Optional.empty());
		
		//THEN
		Assertions.assertThrows(ResourceNotExistException.class, () -> userDetailsService.loadUserByUsername("userName"));
	}
}