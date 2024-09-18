package com.blogifyr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogifyr.payload.ApiResponse;
import com.blogifyr.payload.CategoryDto;
import com.blogifyr.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//post-create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
	
	 	 CategoryDto created = this.categoryService.createCategory(categoryDto);
		
	 	 return new ResponseEntity<>(created,HttpStatus.OK);	
	}
	
	
	//put-update category
	@PutMapping("/{cid}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("cid") int cid){
		
		 CategoryDto updated =  this.categoryService.updateCategory(categoryDto,cid);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	}
	
	//delete-delete category
	@DeleteMapping("/{cid}")
	public ResponseEntity<ApiResponse> delete(@PathVariable int cid){
		
		this.categoryService.deleteCategory(cid);
		return  new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted",true),HttpStatus.OK);
	}
	//get-get-single-category
	@GetMapping("/{cid}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable int cid){
		
		 CategoryDto dto = this.categoryService.getCategoryById(cid);
		
		 return new ResponseEntity<>(dto,HttpStatus.OK);
	
	}
	//get-allcategory
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAll(){
		
		 List<CategoryDto> dtos = this.categoryService.getAllCategory();
		 return ResponseEntity.ok(dtos);
	}
}
