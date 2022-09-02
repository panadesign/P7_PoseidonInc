package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.util.PasswordConstraintValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplTest {


	private UserServiceCrudImpl userService;

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private PasswordEncoder mockPasswordEncoder;
	
	@Mock
	private PasswordConstraintValidator mockPasswordConstraintValidator;

	@Mock
	private MyUserDetailsService myUserDetailsService;

	@BeforeEach
	void init() {
		userService = new UserServiceCrudImpl(mockUserRepository, mockPasswordEncoder, myUserDetailsService);
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

		UserAccount newUserAccount = new UserAccount(
				id,
				username,
				plainTextPassword,
				fullname,
				role
		);

		//WHEN
		when(mockUserRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
		when(mockPasswordEncoder.encode(any())).thenReturn(hashedPassword);

		UserAccount userAccountToRegister = userService.add(newUserAccount);

		//THEN
		assertThat(userAccountToRegister)
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
		UserAccount newUserAccount = new UserAccount(1, "bob", "1", "Morane", "ADMIN");

		//WHEN
		when(mockUserRepository.findByUsername("bob")).thenReturn(Optional.of(newUserAccount));

		//THEN
		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.add(newUserAccount));

	}

	@Test
	void deleteUser() {
		//GIVEN
		UserAccount userAccount = new UserAccount(1, "Bob", "1", "Morane", "ADMIN");

		//WHEN
		userService.delete(userAccount.getId());

		//THEN
		verify(mockUserRepository, times(1)).deleteById(userAccount.getId());
	}

	@Test
	void getUserById() {
		//GIVEN
		UserAccount userAccount = new UserAccount(1, "Bob", "1", "Morane", "ADMIN");

		//WHEN
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(userAccount));

		UserAccount userAccountToFind = userService.getById(userAccount.getId());

		//THEN
		Assertions.assertEquals(1, userAccountToFind.getId());
	}

	@Test
	void getUserByIdNotExistException() {
		UserAccount userAccount = new UserAccount(1, "Bob", "1", "Morane", "ADMIN");
		Assertions.assertThrows(ResourceNotExistException.class, () -> userService.getById(userAccount.getId()));
	}

	@Test
	void getAllUsers() {
		//GIVEN
		List<UserAccount> userAccounts = List.of(
				new UserAccount(1, "Bob", "1", "Morane", "ADMIN"),
				new UserAccount(2, "Bla", "1", "Morane", "ADMIN")
		);

		//WHEN
		when(mockUserRepository.findAll()).thenReturn(userAccounts);

		List<UserAccount> allUserAccounts = userService.getAll();

		//THEN
		Assertions.assertEquals(allUserAccounts, userAccounts);
	}

	@Test
	void updateUser() {
		//GIVEN
		UserAccount userAccount = new UserAccount(1, "Bob", "1", "Morane", "ADMIN");
		//WHEN
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(userAccount));

		UserAccount userAccountDto = new UserAccount("John", mockPasswordEncoder.encode("newPassword"), "Mclane", "USER");

		UserAccount userAccountUpdated = userService.update(1, userAccountDto);

		Assertions.assertEquals(1, userAccountUpdated.getId());
		Assertions.assertEquals("John", userAccountUpdated.getUsername());
		Assertions.assertEquals(mockPasswordEncoder.encode("newPassword"), userAccountUpdated.getPassword());
		Assertions.assertEquals("Mclane", userAccountUpdated.getFullname());
		Assertions.assertEquals("USER", userAccountUpdated.getRole());
	}

	@Test
	void updateUserNotExistingException() {
		UserAccount userAccountDto = new UserAccount("John", mockPasswordEncoder.encode("newPassword"), "Mclane", "USER");
		Assertions.assertThrows(ResourceNotExistException.class, () -> userService.update(1, userAccountDto));
	}

}