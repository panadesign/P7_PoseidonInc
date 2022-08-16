package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class UserServiceCrudImpl extends AbstractServiceCrud<User, UserRepository>{
	
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceCrudImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User add(User user) {
		if(usernameExist(user.getUsername())) {
			throw new UserAlreadyExistException("This username exist already" + user.getUsername());
		}
		
		String password = passwordEncoder.encode(user.getPassword());
		User newUser =  new User(user.getUsername(), password, user.getFullname(), user.getRole());
		
		log.debug("New user has been created:" + user.getUsername());
		
		return repository.save(newUser);
	}
	
	@Override
	public User update(Integer id, User userDto) {
		User userToUpdate = repository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("User with id " + id + " doesn't exist."));
		
		User updatedUser = userToUpdate.update(userDto);
		repository.save(updatedUser);
		return updatedUser;
	}
	
	private boolean usernameExist(String username) {
		return repository.findByUsername(username).isPresent();
	}
}
