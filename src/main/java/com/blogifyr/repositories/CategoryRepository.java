package com.blogifyr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogifyr.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
}
