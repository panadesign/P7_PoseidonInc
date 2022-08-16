package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.exception.UserAlreadyExistException;
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

		if(userRepository.existsByUsername(user.getUsername())) {
			log.debug("User with this username :" + user.getUsername() + ", already exist");
			throw new UserAlreadyExistException("This username exist already" + user.getUsername());
		}

		String password = passwordEncoder.encode(user.getPassword());
		User newUser =  new User(user.getUsername(), password, user.getFullname(), user.getRole());

		log.debug("New user has been created:" + user.getUsername());

		return userRepository.save(newUser);
	}


	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public User getUserById(Integer  id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("User with id " + id + " doesn't exist."));
	}

	public List<User> getAllUser() {
		log.debug("All users: " + userRepository.findAll());
		return userRepository.findAll();
	}
	
	@Override
	public User updateUser(Integer id, User userDto) {
		User userToUpdate = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotExistException("User with id " + id + " doesn't exist."));
		
		User updatedUser = userToUpdate.update(userDto);
		userRepository.save(updatedUser);
		return updatedUser;
	}
}

