package com.blogifyr.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogifyr.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);
	
}
