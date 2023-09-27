package com.blogspot.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.exceptions.UserException;
import com.blogspot.api.models.Post;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private PostRepository postRepo;
    private UserRepository userRepo;

    @Override
    public String addFavoritePost(int user_id, int post_id) {
        Post post = postRepo.findById(post_id).orElseThrow(() -> new PostException("Post not found."));
        Users user = userRepo.findById(user_id).orElseThrow(() -> new UserException("User not found."));

        if(post.getAuthor().getId() == user.getId())
            throw new PostException("You may favorite your own post.");

        user.getFavorites().add(post);
        userRepo.save(user);
        return "Added to favorites";
    }
}
