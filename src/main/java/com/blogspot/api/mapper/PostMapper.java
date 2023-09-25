package com.blogspot.api.mapper;

import java.util.stream.Collectors;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.models.Post;

public class PostMapper {
    public static Post maptoPost (PostDTO postDTO){
        return Post.builder()
                    .id(postDTO.getId())
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    .createdOn(postDTO.getCreatedOn())
                    .updatedOn(postDTO.getUpdatedOn())
                    .build();
    }

    public static PostDTO maptoPostDTO (Post post){
        return PostDTO.builder()
                    .id(post.getId())
                    .author_id(post.getAuthor().getId())
                    .title(post.getTitle())
                    .tags(post.getTags().stream().map((tag)->tag.getTag()).collect(Collectors.toList()))
                    .content(post.getContent())
                    .createdOn(post.getCreatedOn())
                    .updatedOn(post.getUpdatedOn())
                    .build();
    }
}
