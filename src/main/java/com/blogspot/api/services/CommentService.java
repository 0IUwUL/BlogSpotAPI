package com.blogspot.api.services;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.dto.CommentMessageDTO;

public interface CommentService {
    CommentMessageDTO createComment(int id, CommentDTO commentDTO);

}
