package com.blogifyr.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogifyr.entities.Category;
import com.blogifyr.entities.User;
import com.blogifyr.exceptions.ResourceNotFoundException;
import com.blogifyr.payload.CategoryDto;
import com.blogifyr.payload.UserDto;
import com.blogifyr.repositories.CategoryRepository;
import com.blogifyr.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository; 
	@Autowired
	private ModelMapper mapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 Category category = this.mapper.map(categoryDto, Category.class);
		
		Category createCategory =  this.categoryRepository.save(category);
		 

		return this.mapper.map(createCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		 Category category = this.categoryRepository.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("Category", "ID", categoryId));
	 
	category.setCategoryTitle(categoryDto.getCategoryTitle());
	category.setCategoryDescription(categoryDto.getCategoryDescription());

     Category updated   = 	this.categoryRepository.save(category);
	
     return this.mapper.map(updated, CategoryDto.class);	
     
		
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
	
		 Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","ID", categoryId));
	     
		 return this.mapper.map(category,CategoryDto.class);		
               
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
	    List<Category> categories  = this.categoryRepository.findAll();
	    List<CategoryDto> CategoryDtos = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
		return CategoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		 Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","ID", categoryId));
		
		 this.categoryRepository.delete(category);
		 
	}
	
	public Category dtoToCategory(CategoryDto categoryDto) {

		 Category category = this.mapper.map(categoryDto, Category.class);
		
		 return category;
	}
	public CategoryDto categoryToDto(Category category) {

		  CategoryDto dto = this.mapper.map(category, CategoryDto.class);
		  return dto;
	
	}


}
