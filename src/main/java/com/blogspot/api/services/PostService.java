package com.blogspot.api.services;

import com.blogspot.api.dto.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
}
