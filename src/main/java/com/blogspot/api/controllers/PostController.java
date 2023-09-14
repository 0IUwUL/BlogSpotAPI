package com.blogspot.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.models.Post;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/post/")
public class PostController {

    //get all post
    @GetMapping("posts")
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Test", "test"));

        return ResponseEntity.ok(posts);
    }

    //get specific post
    @GetMapping("{id}")
    public Post getPost(@PathVariable int id){
        return new Post(id, "This is a test", "Testing the test");
    }

    @PostMapping(value="create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        System.out.println(post.getTitle());
        System.out.println(post.getContent());
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Post> updatePost(@RequestBody Post post, @PathVariable("id") int id){
        System.out.println(post.getTitle());
        System.out.println(post.getContent());
        return ResponseEntity.ok(post);

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id){
        System.out.println(id);
        return ResponseEntity.ok("Post "+id+" has deleted.");
    }
    
}
