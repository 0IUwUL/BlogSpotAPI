package com.blogspot.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogspot.api.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    Page<Post> findAllByOrderByCreatedOnDesc(Pageable pagable);
    @Query("SELECT p FROM post p " +
            "LEFT JOIN p.tags t "+
            "WHERE (:title is NULL OR LOWER(p.title) LIKE CONCAT('%', LOWER(:title), '%')) " +
            "AND (:tags IS NULL OR t.tag IN :tags) ")
    Page<Post> searchContext(Pageable pagable, 
                            @Param("title") String title,
                            @Param("tags") List<String> tags);
}
