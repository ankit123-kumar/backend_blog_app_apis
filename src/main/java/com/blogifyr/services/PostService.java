package com.blogifyr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogifyr.entities.Post;
import com.blogifyr.payload.PostDto;
import com.blogifyr.payload.PostResponse;


public interface PostService {

	//create post
	PostDto createPost(PostDto postDto,Integer uid,Integer cid);
	
	//update post
	PostDto updatePost(PostDto postDto, int postId);
	
	//delete
	
	void deletePost(int postId);
	
	//get all posts
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//getPost by id
	
	PostDto getSinglePost(int postId);
	
	//get post by category
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get posts by user
	
	PostResponse getPostsByUser(int userid,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//search
	List<PostDto> searchPosts(String keyword);
}
