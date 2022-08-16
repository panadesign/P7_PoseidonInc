package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


	private UserServiceCrudImpl userService;

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private PasswordEncoder mockPasswordEncoder;

	@BeforeEach
	void init() {
		userService = new UserServiceCrudImpl(mockUserRepository, mockPasswordEncoder);
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

		User userToRegister = userService.add(newUser);

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
		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.add(newUser));

	}

	@Test
	void deleteUser() {
		//GIVEN
		User user = new User(1, "Bob", "1", "Morane", "ADMIN");

		//WHEN
		userService.delete(user.getId());

		//THEN
		verify(mockUserRepository, times(1)).deleteById(user.getId());
	}

	@Test
	void getUserById() {
		//GIVEN
		User user = new User(1, "Bob", "1", "Morane", "ADMIN");

		//WHEN
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(user));

		User userToFind = userService.getById(user.getId());

		//THEN
		Assertions.assertEquals(1, userToFind.getId());
	}

	@Test
	void getUserByIdNotExistException() {
		User user = new User(1, "Bob", "1", "Morane", "ADMIN");
		Assertions.assertThrows(ResourceNotExistException.class, () -> userService.getById(user.getId()));
	}

	@Test
	void getAllUsers() {
		//GIVEN
		List<User> users = new ArrayList<>();
		User user = new User(1, "Bob", "1", "Morane", "ADMIN");
		User user2 = new User(2, "Bla", "1", "Morane", "ADMIN");
		users.add(user);
		users.add(user2);

		//WHEN
		when(mockUserRepository.findAll()).thenReturn(users);

		List<User> allUsers = userService.getAll();

		//THEN
		Assertions.assertEquals(allUsers, users);
	}

}