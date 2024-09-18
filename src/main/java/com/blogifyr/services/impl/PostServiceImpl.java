package com.blogifyr.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.ldap.PagedResultsResponseControl;

import org.hibernate.ResourceClosedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogifyr.entities.Category;
import com.blogifyr.entities.Post;
import com.blogifyr.entities.User;
import com.blogifyr.exceptions.ResourceNotFoundException;
import com.blogifyr.payload.PostDto;
import com.blogifyr.payload.PostResponse;
import com.blogifyr.repositories.CategoryRepository;
import com.blogifyr.repositories.PostRepo;
import com.blogifyr.repositories.UserRepository;
import com.blogifyr.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper mapper;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(
					()-> new ResourceNotFoundException("User", "Id", userId));
		 Category category = this.categoryRepository.findById(categoryId).orElseThrow(
			    	()-> new ResourceNotFoundException("Category", "Id", categoryId));
		  
		 Post post = this.mapper.map(postDto, Post.class);

		 post.setImageName("default.png");
		 post.setAddedDate(new Date());
		 post.setUser(user);
		 post.setCategory(category);
		 
		 Post post1 = this.postRepo.save(post);
	   
		 return this.mapper.map(post1, PostDto.class);
	 
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		
		 Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		
		 post.setTitle(postDto.getTitle());
		 post.setContent(postDto.getContent());
		 post.setImageName(postDto.getImageName());
		
		  Post post1 = this.postRepo.save(post);
		 return  this.mapper.map(post1, PostDto.class);
		 
	}

	@Override
	public void deletePost(int postId) {
		
		  Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
	       
		  this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			  sort = Sort.by(sortBy).ascending();
		}else {
			 sort = Sort.by(sortBy).descending();
		}
		 Pageable p =  PageRequest.of(pageNumber, pageSize,sort); 
		  Page<Post> pagePost= this.postRepo.findAll(p);
		  List<Post> posts =  pagePost.getContent();
		  List<PostDto> postDtos = posts.stream().map(post -> this.postToDto(post)).collect(Collectors.toList());
       
		  PostResponse postResponse = new PostResponse();
		  
		 
		  postResponse.setContent(postDtos);
		  
		  postResponse.setPageNumber(pagePost.getNumber());
		  postResponse.setPageSize(pagePost.getSize());
		  postResponse.setTotalPages(pagePost.getTotalPages());
		  postResponse.setLastPage(pagePost.isLast());
		  postResponse.setTotalElements(pagePost.getTotalElements());
		  return postResponse;
	}

	@Override
	
	public PostDto getSinglePost(int postId) {
		
		 Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		 
		 return this.mapper.map(post,PostDto.class);
		
		
		
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		 Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		 List<Post> posts = this.postRepo.findAllByCategory(cat);
		 List<PostDto> dtos= posts.stream().map((post)->  this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
	      
		 return dtos;
	}

	@Override
	public PostResponse getPostsByUser(int userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		
		  Pageable p  = PageRequest.of(pageNumber, pageSize);
	   
		  User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		  // Page<Post> pagePost = this.postRepo.findAll(p);
		 
		  Page<Post> pagePosts =  (Page<Post>) this.postRepo.findAllByUser(user);
		   
		  List<Post> posts= pagePosts.getContent();

		  
		  List<PostDto> Postdtos= posts.stream().map((post)->  this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		  
		  PostResponse postResponse = new PostResponse();
		  
		  postResponse.setContent(Postdtos);
		  postResponse.setPageNumber(pagePosts.getNumber());
		  postResponse.setPageSize(pagePosts.getSize());
		  postResponse.setTotalPages(pagePosts.getTotalPages());
		  postResponse.setTotalElements(pagePosts.getTotalElements());
		  postResponse.setLastPage(pagePosts.isLast());
		  return postResponse ;
		  
}
		
  
	public PostDto postToDto(Post post) {
		
		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		 List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		  List<PostDto> dtos = posts.stream().map(post-> this.postToDto(post)).collect(Collectors.toList());
		return dtos;
	}
}

