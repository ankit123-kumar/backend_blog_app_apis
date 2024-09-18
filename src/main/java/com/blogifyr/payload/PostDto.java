package com.blogifyr.payload;

import java.util.HashSet;
import java.util.Set;

import com.blogifyr.entities.Category;
import com.blogifyr.entities.Comment;
import com.blogifyr.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private int postId;
	private String title;
	private String content;
	private String imageName;
	private String addedDate;
	private UserDto user;
	private CategoryDto category;
	
	
	private Set<CommentDto> comments = new HashSet<>();
}
