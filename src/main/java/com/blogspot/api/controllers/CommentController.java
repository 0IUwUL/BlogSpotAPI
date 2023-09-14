package com.blogspot.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.dto.CommentMessageDTO;
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
    public ResponseEntity<CommentMessageDTO> createComment(@PathVariable("id") int id, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(id, commentDTO), HttpStatus.CREATED);
    }
}
