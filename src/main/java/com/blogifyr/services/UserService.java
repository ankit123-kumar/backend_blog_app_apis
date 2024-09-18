package com.blogifyr.services;

import java.util.List;

import com.blogifyr.payload.UserDto;

public interface UserService {

	 UserDto registerNewUser(UserDto user);
	 UserDto createUser(UserDto user);
	 UserDto updateUser(UserDto user,Integer userid);
	 UserDto getUserById(Integer userid);
	 List<UserDto> getAllUser();
	 void deleteUser(Integer userId);
}
