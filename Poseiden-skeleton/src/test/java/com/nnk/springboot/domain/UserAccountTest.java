package com.nnk.springboot.domain;

import com.nnk.springboot.exception.InvalidPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

class UserAccountTest {
	
	private Validator validator;
	
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
	
	private static Object[][] invalidStringProvider() {
		return new Object[][]{
				{null},
				{""},
				{" "},
				{"    "}
		};
	}
	
	@BeforeEach
	void setUp() {
		var validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@ParameterizedTest
	@MethodSource("invalidStringProvider")
	void usernameWithInvalidString(String string) {
		//GIVEN
		UserAccount newUserAccount = new UserAccount(
				1,
				string,
				"Azerty123!",
				"Morane",
				"ADMIN"
		);
		
		//WHEN
		var violations = validator.validate(newUserAccount);
		
		//THEN
		assertThat(violations)
				.isNotEmpty()
				.hasSize(1)
				.first()
				.satisfies(v -> assertThat(v.getMessage()).hasToString("Username is mandatory"));
	}
	
	@ParameterizedTest
	@MethodSource("invalidStringProvider")
	void fullnameWithInvalidString(String string) {
		//GIVEN
		UserAccount newUserAccount = new UserAccount(
				1,
				"bob",
				"Azerty123!",
				string,
				"ADMIN"
		);
		
		//WHEN
		var violations = validator.validate(newUserAccount);
		
		//THEN
		assertThat(violations)
				.isNotEmpty()
				.hasSize(1)
				.first()
				.satisfies(v -> assertThat(v.getMessage()).hasToString("FullName is mandatory"));
	}
	
	
	@ParameterizedTest
	@MethodSource("invalidPasswordProvider")
	void userAccountWithInvalidPassword(String invalidPassword) {
		//GIVEN
		UserAccount newUserAccount = new UserAccount(
				1,
				"Bob",
				invalidPassword,
				"Morane",
				"ADMIN"
		);
		
		//WHEN
		var violations = validator.validate(newUserAccount);
		
		//THEN
		assertThat(violations)
				.isNotEmpty()
				.hasSize(1)
				.first()
				.satisfies(v -> assertThat(v.getMessage()).hasToString("Invalid password ! Password must contain at least 8 character, one or mmore uppercase, number and special character."));
	}
}