package com.nnk.springboot.service;

import com.nnk.springboot.Exception.ResourceNotExistException;
import com.nnk.springboot.Exception.UserAlreadyExistException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@Log4j2
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User addUser(User user) {

		if(usernameExist(user.getUsername())) {
			log.debug("User with this username :" + user.getUsername() + ", already exist");
			throw new UserAlreadyExistException("This username exist already" + user.getUsername());
		}

		String password = passwordEncoder.encode(user.getPassword());
		User newUser =  new User(user.getId(), user.getUsername(), password, user.getFullname(), user.getRole());

		log.debug("New user has been created:" + user.getUsername());

		return userRepository.save(newUser);
	}


	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public User getUserById(User user) {
		if(!userRepository.findById(user.getId()).isPresent()) {
			throw new ResourceNotExistException("User with id " + user.getId() + " doesn't exist.");
		}

		return user;
	}

	public List<User> getAllUser() {
		log.debug("All users: " + userRepository.findAll());
		return userRepository.findAll();
	}

	private boolean usernameExist(String username) {
		return userRepository.findByUsername(username).isPresent();
	}
}

