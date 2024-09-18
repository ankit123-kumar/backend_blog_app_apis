package com.blogifyr.services;

import java.util.List;

import com.blogifyr.payload.CategoryDto;
import com.blogifyr.payload.UserDto;

public interface CategoryService {


	 CategoryDto createCategory(CategoryDto categoryDto);
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	 CategoryDto getCategoryById(Integer categoryId);
	 List<CategoryDto> getAllCategory();
	 void deleteCategory(Integer categoryId);
	
}
