package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
	
	private static Object[][] validPasswordProvider() {
		return new Object[][]{

		};
	}
	
	private static Object[][] invalidPasswordProvider() {
		return new Object[][]{
				{"aA0yyyyyyyyyyyyyyyyy"}, //TOO SHORT AND WITHOUT SPECIAL CHARACTER
				{"aa0!"},//TOO SHORT AND WITHOUT UPPERCASE
				{"aAA!"},//TOO SHORT AND WITHOUT NUMBER
				{"RTT0!"},//TOO SHORT AND WITHOUT LOWERCASE
				{"aA00ERJERE"}, //WITHOUT SPECIAL CHARACTER
				{"jfdfdsRsf__"}, //WITHOUT NUMBER
				{"jfdfds3434sf__"}, //WITHOUT UPPERCASE
				{"AAAAAA343AA__"}, //WITHOUT LOWERCASE
				{"AAAAAA34 3AAvvv__"}, //WITH WHITE SPACE
				{"aaaaaaaaaaaaaaaaaa"}, //ONLY LOWERCASE
				{"AAAAAAAAAAAAAAAAA"},//ONLY UPPERCASE
				{"000000000000000"},//ONLY NUMBER
				{"!!!!!!!!!!!!!!!!!!"},//ONLY SPECIAL CHARACTER
				{"Test_2"}, //TOO SHORT
				{""}, //EMPTY
				{" "} //WITH WHITE SPACE
		};
	}

	@BeforeEach
	void init() {
		userService = new UserServiceCrudImpl(mockUserRepository, mockPasswordEncoder);
	}
	
	@ParameterizedTest
	@MethodSource("invalidPasswordProvider")
	void addUserWithInvalidPassword(String invalidPassword) {
		//GIVEN
		UserAccount newUserAccount = new UserAccount(
				1,
				"Bob",
				invalidPassword,
				"Morane",
				"ADMIN"
		);
		
		//WHEN
		Executable executable = () -> userService.add(newUserAccount);
		
		//THEN
		Assertions.assertThrows(InvalidPasswordException.class, executable);
	}
	
	@Test
	void addUser() {
		//GIVEN
		int id = 12;
		String username = "Bob";
		String fullname = "Morane";
		String role = "ADMIN";
		
		String rawPassword = "validRawPassword123_";
		String hashedPassword = "hashedPassword";

		UserAccount newUserAccount = new UserAccount(
				id,
				username,
				rawPassword,
				fullname,
				role
		);

		//WHEN
		when(mockPasswordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
		when(mockUserRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
		
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
		UserAccount userAccount = new UserAccount(1, "Bob", "123456", "Morane", "ADMIN");
		
		String rawPassword = "validRawPassword123-";
		String hashedPassword = "hashedPassword";
		UserAccount userAccountDto = new UserAccount("John",rawPassword, "Mclane", "USER");
		
		//WHEN
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(userAccount));
		when(mockPasswordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

		UserAccount userAccountUpdated = userService.update(1, userAccountDto);

		Assertions.assertEquals(1, userAccountUpdated.getId());
		Assertions.assertEquals("John", userAccountUpdated.getUsername());
		Assertions.assertEquals(hashedPassword, userAccountUpdated.getPassword());
		Assertions.assertEquals("Mclane", userAccountUpdated.getFullname());
		Assertions.assertEquals("USER", userAccountUpdated.getRole());
	}

	@Test
	void updateUserNotExistingException() {
		UserAccount userAccountDto = new UserAccount("John", mockPasswordEncoder.encode("newPassword"), "Mclane", "USER");
		Assertions.assertThrows(ResourceNotExistException.class, () -> userService.update(1, userAccountDto));
	}


	@Test
	void updateUserInvalidPasswordException() {
		//GIVEN
		UserAccount userAccount = new UserAccount(1, "Bob", "123456", "Morane", "ADMIN");

		String rawPassword = "invalidPassword";
		String hashedPassword = "hashedPassword";
		UserAccount userAccountDto = new UserAccount("Bob",rawPassword, "Morane", "ADMIN");

		//WHEN
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(userAccount));

		//THEN
		Assertions.assertThrows(InvalidPasswordException.class, () -> userService.update(1, userAccountDto));
	}
}