package com.nnk.springboot.service;


import com.nnk.springboot.domain.UserAccount;
import com.nnk.springboot.exception.ResourceNotExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type My user details service.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserAccount userAccount = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotExistException("User doesn't exist with username = " + username));
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(userAccount.getRole());
		
		return new User(userAccount.getUsername(), userAccount.getPassword(), List.of(role));
	}
}