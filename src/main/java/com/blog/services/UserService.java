package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {

	// Register User
	UserDto registerNewUser(UserDto user, String role);

	// Create
	UserDto createUser(UserDto userdto);

	// Update
	UserDto updateUser(UserDto userdto, Integer userId);

	// get one user
	UserDto getUserById(Integer userId);

	// get all users
	List<UserDto> getAllUsers();

	// Delete
	void deleteUser(Integer userId);

}
