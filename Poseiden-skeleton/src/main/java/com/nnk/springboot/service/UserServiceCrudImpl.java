package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.util.PasswordConstraintValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserServiceCrudImpl extends AbstractServiceCrud<UserAccount, UserRepository>{
	
	private final PasswordEncoder passwordEncoder;
	private final MyUserDetailsService myUserDetailsService;
	private final PasswordConstraintValidator passwordConstraintValidator;

	public UserServiceCrudImpl(UserRepository repository, PasswordEncoder passwordEncoder, MyUserDetailsService myUserDetailsService, PasswordConstraintValidator passwordConstraintValidator) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
		this.myUserDetailsService = myUserDetailsService;
		this.passwordConstraintValidator = passwordConstraintValidator;
	}
	
	@Override
	public UserAccount add(UserAccount userAccount) {
		if(usernameExist(userAccount.getUsername())) {
			throw new UserAlreadyExistException("This username exist already" + userAccount.getUsername());
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
		
		UserAccount updatedUserAccount = userAccountToUpdate.update(userAccountDto);
		repository.save(updatedUserAccount);
		return updatedUserAccount;
	}
	
	private boolean usernameExist(String username) {
		return repository.findByUsername(username).isPresent();
	}
}
