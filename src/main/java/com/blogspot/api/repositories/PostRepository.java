package com.blogspot.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogspot.api.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findAllByOrderByUpdatedOn();
    
}
