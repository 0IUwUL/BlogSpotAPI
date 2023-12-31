package com.blogspot.api.services;

import java.util.List;

import com.blogspot.api.dto.CommentDTO;

public interface CommentService {
    CommentDTO createComment(int id, CommentDTO commentDTO);
    List<CommentDTO> getAllComments(int id);
    CommentDTO getComment(int post_id, int id);
    CommentDTO updateComment(int post_id, int id, CommentDTO commentDTO);
    void deleteComment(int post_id, int id);
}
