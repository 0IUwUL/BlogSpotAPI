package com.blogspot.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.services.ProfileService;

@Controller
@RequestMapping("/api/user")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/favorite/{user_id}/post/{post_id}")
    public ResponseEntity<String> addFavoritePost(@PathVariable("user_id") int user_id, @PathVariable("post_id") int post_id){
        return new ResponseEntity<>(profileService.addFavoritePost(user_id, post_id), HttpStatus.OK);
    }

    @GetMapping("/follow/{user_id}/{author_id}")
    public ResponseEntity<String> followAuthor(@PathVariable("user_id") int user_id, @PathVariable("author_id") int author_id){
        return new ResponseEntity<>(profileService.addFollower(user_id, author_id), HttpStatus.OK);
    }

    @GetMapping("/favorites/{user_id}")
    public ResponseEntity<List<PostDTO>> getAllFavoritePosts(@PathVariable("user_id") int id){
        return new ResponseEntity<>(profileService.getFavorites(id), HttpStatus.OK);
    }
}
