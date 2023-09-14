package com.blogspot.api.services.impl;

import org.springframework.stereotype.Service;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.PostService;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());

        Post newPost = postRepo.save(post);
        PostDTO postResponse = new PostDTO();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setContent(newPost.getContent());
        postResponse.setCreatedOn(newPost.getCreatedOn());
        postResponse.setUpdatedOn(newPost.getUpdatedOn());
        return postResponse;
    }
    
}
