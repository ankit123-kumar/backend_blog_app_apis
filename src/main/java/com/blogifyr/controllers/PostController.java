package com.blogifyr.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogifyr.entities.Post;
import com.blogifyr.helper.AppConstants;
import com.blogifyr.payload.ApiResponse;
import com.blogifyr.payload.PostDto;
import com.blogifyr.payload.PostResponse;
import com.blogifyr.services.ImageService;
import com.blogifyr.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${project.image}")
	private String path;
	//handler for create a post
  
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
	
		 PostDto post = this.postService.createPost(postDto, userId, categoryId);
		 
		 return new ResponseEntity<PostDto>(post,HttpStatus.CREATED);
		
	}
	
	//handler for get post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			
			){
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new  ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
    
	//handler for get single post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable  int postId){
		
		PostDto post  = this.postService.getSinglePost(postId);
	
		return new  ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//handler for get posts by category Id
	@GetMapping("/posts/category/{cid}")
	public ResponseEntity <List<PostDto>> getPostsByCategoryId(@PathVariable int cid){
		
		List<PostDto> posts= this.postService.getPostsByCategory(cid);
		return new ResponseEntity(posts,HttpStatus.OK);
	}
	
	//handler for get posts by user Id
		@GetMapping("/posts/user/{uid}")
		public ResponseEntity <PostResponse> getPostsByUserId(@PathVariable int uid,
				
				@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
				@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
				@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
				
				)
				
				{
			
			PostResponse postResponse= this.postService.getPostsByUser(uid,pageNumber,pageSize,sortBy,sortDir);
			return new ResponseEntity(postResponse,HttpStatus.OK);
		}
		
		//handler for delete a post
		@DeleteMapping("/posts/delete/{pid}")
		public ResponseEntity<ApiResponse> deletePost(@PathVariable int pid){
			
			this.postService.deletePost(pid);
			
			ApiResponse apiResponse = new ApiResponse("post deleted successfully",false);
			
			return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
		}
		
		//handler for update a post
		@PutMapping("/posts/update/{pid}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable int pid){
			
			 PostDto updatedPost = this.postService.updatePost(postDto, pid);
			return new ResponseEntity(updatedPost,HttpStatus.OK);
		}
		
		//handler for searching
		
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keywords){

			List<PostDto>  dtos= this.postService.searchPosts(keywords);
			return new ResponseEntity<List<PostDto>>(dtos,HttpStatus.OK);
		}
		
		@PostMapping("/posts/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				
				@PathVariable Integer postId,
				@RequestParam("image") MultipartFile image) throws IOException{
			
			PostDto postDto = this.postService.getSinglePost(postId);
		      
			String fileName = this.imageService.uploadImage(path, image);
			  postDto.setImageName(fileName);
	       	    PostDto updatedPost =  this.postService.updatePost(postDto, postId);
		
		      return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		}
		
		
		//method to serve image
         @GetMapping(value="/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException {
        
			InputStream resource = this.imageService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
			

		}
}