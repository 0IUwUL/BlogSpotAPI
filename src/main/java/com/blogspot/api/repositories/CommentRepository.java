package com.blogspot.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogspot.api.models.Comment;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByPostId(int postId);
}
