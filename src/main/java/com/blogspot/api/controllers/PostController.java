package com.blogspot.api.controllers;

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
    public ResponseEntity<PostDTO> getPost(@PathVariable int id){
        // PostDTO postDTO = postService.getPost(id);
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping(value="create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("id") int id){
        PostDTO response = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id){
        System.out.println(id);
        return ResponseEntity.ok("Post "+id+" has deleted.");
    }
    
}
