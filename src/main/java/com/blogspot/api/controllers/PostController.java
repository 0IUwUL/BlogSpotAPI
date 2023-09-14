package com.blogspot.api.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.models.Post;
import com.blogspot.api.services.PostService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/post/")
public class PostController {
    private PostService postService;

    
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //get all post
    @GetMapping("posts")
    public ResponseEntity<List<PostDTO>> getPosts(){
        // List<PostDTO> posts = postService.getAllPost();
        // return ResponseEntity.ok(posts);
        //one liner
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    //get specific post
    @GetMapping("{id}")
    public Post getPost(@PathVariable int id){
        return new Post(id, "This is a test", "Testing the test", LocalDateTime.now(), LocalDateTime.now());
    }

    @PostMapping(value="create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {

        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
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
