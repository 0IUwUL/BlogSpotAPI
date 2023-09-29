package com.blogspot.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;
import com.blogspot.api.dto.SearchDTO;
import com.blogspot.api.services.PostService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/post/")
public class PostController {
    private PostService postService;

    
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //get all post
    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("")
    public ResponseEntity<PostResponse> getPosts(
        @RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        // List<PostDTO> posts = postService.getAllPost(pageNo, pageSize);
        // return ResponseEntity.ok(posts);
        //one liner
        return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize), HttpStatus.OK);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    @PostMapping("search")
    public ResponseEntity<PostResponse> GetSearchResult(
        @RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
        @RequestBody SearchDTO params
    ){
        return new ResponseEntity<>(postService.getSearchResult(pageNo, pageSize, params), HttpStatus.OK);
    }

    //get specific post
    @GetMapping("{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable int id){
        // PostDTO postDTO = postService.getPost(id);
        return ResponseEntity.ok(postService.getPost(id));
    }

    /**
     * @param postDTO
     * @return
     */
    @PostMapping(value="create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    /**
     * @param postDTO
     * @param id
     * @return
     */
    @PutMapping("update/{id}/{author_id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, 
                                                @PathVariable("id") int id, 
                                                @PathVariable("author_id") int author_id){
        return ResponseEntity.ok(postService.updatePost(id, postDTO, author_id));
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}/{author_id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id, @PathVariable("author_id") int author_id){
        postService.deletePost(id, author_id);
        return new ResponseEntity<>("Post "+id+" has been deleted.", HttpStatus.OK);
    }
    
}
