package com.blogifyr.controllers;



import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogifyr.config.UserDetailsServiceImpl;
import com.blogifyr.exceptions.ApiException;
import com.blogifyr.payload.UserDto;
import com.blogifyr.security.JwtAuthResponse;
import com.blogifyr.security.JwtRequest;
import com.blogifyr.security.JwtTokenHelper;
import com.blogifyr.services.UserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserService userService;
	//@Autowired
	//private Logger logger= (Logger) LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtRequest request)throws Exception{
		
		System.out.println(request.getUsername());
		this.doAuthenticate(request.getUsername(), request.getPassword());
		 UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
	 JwtAuthResponse response =  new JwtAuthResponse();
	 response.setToken(token);
	 response.setUsername(userDetails.getUsername());
		
	 return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}
	private void doAuthenticate(String email,String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			
			this.authenticationManager.authenticate(authentication);
			
		} catch (BadCredentialsException e) {
			
			System.out.println("Invalid details");
			throw new ApiException("invalid username and password");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
	   UserDto registeredUser  = 	this.userService.registerNewUser(userDto);
          
	   return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
}
