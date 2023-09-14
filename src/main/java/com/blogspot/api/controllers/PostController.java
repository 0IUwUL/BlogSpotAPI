package com.blogspot.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.models.Post;

@RestController
@RequestMapping("/post/")
public class PostController {
    @GetMapping("posts")
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Test", "test"));

        return ResponseEntity.ok(posts);
    }
}
