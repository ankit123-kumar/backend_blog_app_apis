package com.blogifyr.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogifyr.entities.Comment;
import com.blogifyr.entities.Post;
import com.blogifyr.entities.User;
import com.blogifyr.exceptions.ResourceNotFoundException;
import com.blogifyr.payload.CommentDto;
import com.blogifyr.repositories.CommentRepo;
import com.blogifyr.repositories.PostRepo;
import com.blogifyr.repositories.UserRepository;
import com.blogifyr.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		
		 Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		 User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		  Comment comment = this.mapper.map(commentDto, Comment.class);
		 
		  comment.setPost(post);
		  comment.setUser(user);
		  
		   Comment savedComment = this.commentRepo.save(comment);
		  
		  return this.mapper.map(savedComment, CommentDto.class);
		  	}

	@Override
	public void deleteComment(Integer commentId) {
		
		 Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
		 
	}

}
