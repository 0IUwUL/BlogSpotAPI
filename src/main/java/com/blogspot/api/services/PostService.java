package com.blogspot.api.services;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPost(int pageNo, int pageSize);
    PostDTO getPost(int id);
    PostDTO updatePost(int id, PostDTO post);
    void deletePost(int id);
}
