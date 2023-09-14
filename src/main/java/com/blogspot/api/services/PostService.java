package com.blogspot.api.services;

import java.util.List;

import com.blogspot.api.dto.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPost();
    PostDTO getPost(int id);
    PostDTO updatePost(int id, PostDTO post);
    void deletePost(int id);
}
