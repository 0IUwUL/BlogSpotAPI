package com.blogspot.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.services.CommentService;

@RestController
@RequestMapping("/comment/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{id}/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDTO> createComment(@PathVariable("id") int id, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(id, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<CommentDTO>> findCommentsofPost(@PathVariable("id") int id){
        return new ResponseEntity<List<CommentDTO>>(commentService.getAllComments(id), HttpStatus.OK);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<CommentDTO> findCommentofPost(@PathVariable("id") int id){
        return new ResponseEntity<CommentDTO>(commentService.getComment(id), HttpStatus.OK);
    }
}
