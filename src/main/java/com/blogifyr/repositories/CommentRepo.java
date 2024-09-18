package com.blogifyr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogifyr.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
