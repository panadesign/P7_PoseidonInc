package com.nnk.springboot.service;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotExistException("User doesn't exist with username = " + username));
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
		
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(role));
	}
}