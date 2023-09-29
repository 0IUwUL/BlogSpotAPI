package com.blogspot.api.services;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;
import com.blogspot.api.dto.SearchDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPost(int pageNo, int pageSize);
    PostDTO getPost(int id);
    PostDTO updatePost(int id, PostDTO post, int author_id);
    void deletePost(int id, int author_id);
    PostResponse getSearchResult(int pageNo, int pageSize, SearchDTO params);
}
