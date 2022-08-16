package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {
	User addUser(User user);
	void deleteUser(Integer id);
	List<User> getAllUser();
	User getUserById(Integer id);
	User updateUser(Integer id, User user);
;}
