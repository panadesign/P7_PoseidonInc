package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * The type User service crud.
 */
@Log4j2
@Component
public class UserServiceCrudImpl extends AbstractServiceCrud<UserAccount, UserRepository>{
	
	private final PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new User service crud.
	 *
	 * @param repository      the repository
	 * @param passwordEncoder the password encoder
	 */
	public UserServiceCrudImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserAccount add(UserAccount userAccount) {
		if(usernameExist(userAccount.getUsername())) {
			throw new UserAlreadyExistException("This username exist already: " + userAccount.getUsername());
		}

		if(!isValidPassword(userAccount.getPassword())) {
			throw new InvalidPasswordException("Invalid password");
		}


		String password = passwordEncoder.encode(userAccount.getPassword());
		UserAccount newUserAccount =  new UserAccount(userAccount.getUsername(), password, userAccount.getFullname(), userAccount.getRole());

		log.debug("New user has been created:" + userAccount.getUsername());

		return repository.save(newUserAccount);
	}
	
	@Override
	public UserAccount update(Integer id, UserAccount userAccountDto) {
		UserAccount userAccountToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("User with id " + id + " doesn't exist."));


		if(!isValidPassword(userAccountToUpdate.getPassword())) {
			throw new InvalidPasswordException("Invalid password");
		}
		
		userAccountDto.setPassword(passwordEncoder.encode(userAccountDto.getPassword()));
		
		UserAccount updatedUserAccount = userAccountToUpdate.update(userAccountDto);
		repository.save(updatedUserAccount);
		return updatedUserAccount;
	}
	
	private boolean usernameExist(String username) {
		return repository.findByUsername(username).isPresent();
	}
	
	private static boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>_])(?=\\S+$).{8,60}$";
		return Pattern.compile(regex).matcher(password).matches();
	}
}
