package com.blogspot.api.services;

import java.util.List;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.ProfileDTO;

public interface ProfileService {
    String addFavoritePost(int user_id, int post_id);
    String addFollower(int user_id, int author_id);
    List<PostDTO> getFavorites(int id);
    ProfileDTO getProfile(int id);
}
