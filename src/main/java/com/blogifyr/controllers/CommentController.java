package com.blogifyr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogifyr.entities.Comment;
import com.blogifyr.payload.ApiResponse;
import com.blogifyr.payload.CommentDto;
import com.blogifyr.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment
			
			,@PathVariable int postId,@PathVariable int userId
			){
	
		 CommentDto createdComment = this.commentService.createComment(comment, postId, userId);
		
		 return new ResponseEntity<CommentDto>(createdComment,HttpStatus.OK);
	
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable int commentId){
	
		  this.commentService.deleteComment(commentId);
		
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	
	}

	
}
