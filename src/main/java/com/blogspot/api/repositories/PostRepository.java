package com.blogspot.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogspot.api.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    Page<Post> findAllByOrderByCreatedOnDesc(Pageable pagable);
    @Query("SELECT p from post p WHERE p.title LIKE CONCAT('%', :query, '%')")
    List<Post> searchPost(String query);
}
