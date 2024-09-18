package com.blogifyr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogifyr.entities.User;
import com.blogifyr.repositories.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired	
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
      User user  =  this.userRepo.findByEmail(username);
		
         CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
         return customUserDetails;
	}
}
