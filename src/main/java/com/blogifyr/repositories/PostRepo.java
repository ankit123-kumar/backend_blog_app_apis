package com.blogifyr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogifyr.entities.Category;
import com.blogifyr.entities.Post;
import com.blogifyr.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer> {

	List<Post> findAllByUser(User user);
	List<Post> findAllByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

	}
