package com.blogspot.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogspot.api.models.Tags;

public interface TagRepository extends JpaRepository<Tags, Integer> {

    @Query("SELECT t FROM tags t WHERE t.tag = :tag")
    Tags findByGivenTag(String tag);
    
}
