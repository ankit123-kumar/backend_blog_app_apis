package com.blogifyr.services;

import com.blogifyr.entities.Comment;
import com.blogifyr.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment,Integer postId,Integer userId);
	void deleteComment(Integer commentId);
}
