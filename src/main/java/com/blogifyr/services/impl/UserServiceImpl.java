package com.blogifyr.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogifyr.entities.Role;
import com.blogifyr.entities.User;
import com.blogifyr.payload.UserDto;
import com.blogifyr.repositories.RoleRepo;
import com.blogifyr.repositories.UserRepository;
import com.blogifyr.services.UserService;
import com.blogifyr.exceptions.*;
import com.blogifyr.helper.AppConstants;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private ModelMapper modelmap;
	
	@Autowired
	private RoleRepo role;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);

		User user1 = this.userRepository.save(user);

		UserDto userDto1 = this.userToDto(user1);

		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		
		  User user = this.userRepository.findById(userId).orElseThrow(()-> new
		  ResourceNotFoundException("User","Id",userId));
		  
		  user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		  user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		  
		  User updateUser = this.userRepository.save(user); 
		  UserDto userDto1 = this.userToDto(updateUser); return userDto1;
		 
		
		/*
		 * User user= this.userRepository.findById(userId).get();
		 * 
		 * if(user!=null) {
		 * 
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 * 
		 * User updateUser = this.userRepository.save(user); UserDto userDto1 =
		 * this.userToDto(updateUser); return userDto1;
		 * 
		 * }else {
		 * 
		 * throw new ResourceNotFoundException("User","Id",userId); }
		 */
		  
		  }

	@Override
	public UserDto getUserById(Integer userid) {
		User user = this.userRepository.findById(userid).orElseThrow(()-> new
				  ResourceNotFoundException("User","Id",userid));
				
		  UserDto userDto = this.userToDto(user);
		return userDto;
		// UserDto userDto = this.userToDto(user);
		
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId).get();
		if (user != null) {
			this.userRepository.delete(user);

		} else {
			throw new ResourceNotFoundException("User", "Id", userId);
		}

	}

	public User dtoToUser(UserDto userDto) {

		 User user = this.modelmap.map(userDto, User.class);
		
		 return user;
		/*
		 * User user = new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 * 
		 * return user;
		 */

	}

	public UserDto userToDto(User user) {

		  UserDto userDto = this.modelmap.map(user, UserDto.class);
		  return userDto;
		/*
		 * UserDto userDto = new UserDto(); userDto.setId(user.getId());
		 * userDto.setName(user.getName()); userDto.setEmail(user.getEmail());
		 * userDto.setPassword(user.getPassword()); userDto.setAbout(user.getAbout());
		 * 
		 * return userDto;
		 */
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		 User user = this.modelmap.map(userDto, User.class);
		
		 user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		 
		  Role role = this.role.findById(AppConstants.NORMAL_USER).get();
		  user.getRole().add(role);
		 User newUser = this.userRepository.save(user);
		 
		return this.modelmap.map(newUser, UserDto.class);
		
	}

}
