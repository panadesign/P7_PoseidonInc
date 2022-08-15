package com.nnk.springboot.service;

import com.nnk.springboot.Exception.UserAlreadyExistException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Spy
	UserService userService;

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private PasswordEncoder mockPasswordEncoder;

	@BeforeEach
	void init() {
		userService = new UserServiceImpl(mockUserRepository, mockPasswordEncoder);
	}

	@Test
	void addUser() {
		//GIVEN
		int id = 12;
		String username = "Bob";
		String fullname = "Morane";
		String role = "ADMIN";
		String plainTextPassword = "123";
		String hashedPassword = "456";

		User newUser = new User(
				id,
				username,
				plainTextPassword,
				fullname,
				role
		);

		//WHEN
		when(mockUserRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
		when(mockPasswordEncoder.encode(any())).thenReturn(hashedPassword);

		User userToRegister = userService.addUser(newUser);

		//THEN
		assertThat(userToRegister)
				.satisfies(u -> {
					assertThat(u.getUsername()).hasToString(username);
					assertThat(u.getFullname()).hasToString(fullname);
					assertThat(u.getRole()).hasToString(role);
					assertThat(u.getPassword()).hasToString(hashedPassword);
				});
	}

	@Test
	void addUserAlreadyExistException() {
		//GIVEN
		User newUser = new User(1, "bob", "1", "Morane", "ADMIN");

		//WHEN
		when(mockUserRepository.findByUsername("bob")).thenReturn(Optional.of(newUser));

		//THEN
		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.addUser(newUser));

	}

	@Test
	void deleteUser() {
		//GIVEN
		User user = new User(1, "Bob", "1", "Morane", "ADMIN");

		//WHEN
		userService = Mockito.spy(new UserServiceImpl(mockUserRepository, mockPasswordEncoder));

		userService.deleteUser(user);

		//THEN
		verify(userService).deleteUser(user);
		Assertions.assertTrue(userService.getAllUser().isEmpty());
	}

}