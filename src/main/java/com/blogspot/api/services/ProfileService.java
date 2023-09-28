package com.blogspot.api.services;

import java.util.List;

import com.blogspot.api.dto.PostDTO;

public interface ProfileService {
    String addFavoritePost(int user_id, int post_id);
    String addFollower(int user_id, int author_id);
    List<PostDTO> getFavorites(int id);
}
