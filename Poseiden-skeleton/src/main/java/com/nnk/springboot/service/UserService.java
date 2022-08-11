package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {
	User addUser(User user);
	void deleteUser(User user);
	List<User> getAllUser();
;}
