package com.blogifyr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogifyr.exceptions.ResourceNotFoundException;
import com.blogifyr.payload.ApiResponse;
import com.blogifyr.payload.UserDto;
import com.blogifyr.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	//post-create user
	//handler for create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
	
		 UserDto createdUser = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	//put-update user
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userid") int uid){
		 UserDto updateUser;
		 	  
			  updateUser = this.userService.updateUser(userDto,uid);
		 	  
		   return new ResponseEntity<>(updateUser,HttpStatus.OK);
	}
	
	//delete-delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userid") int uid){
		
		this.userService.deleteUser(uid);
		return  new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
		
	}
	
	//Get get-single-user
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userid") int uid){
         
		 UserDto getUser = this.userService.getUserById(uid);
		    
			 return new ResponseEntity<>(getUser,HttpStatus.OK);
				
		
		
	}
	
	//Get get-all-users
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		 List<UserDto> allUsers = this.userService.getAllUser();
		 return new ResponseEntity<>(allUsers,HttpStatus.OK);
	}
	
}
