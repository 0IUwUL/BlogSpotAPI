package com.blogspot.api.mapper;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.dto.CommentMessageDTO;
import com.blogspot.api.models.Comment;

public class CommentMapper {
    public static Comment maptoComment (CommentDTO commentDTO){
        return Comment.builder()
                    .id(commentDTO.getId())
                    .comment(commentDTO.getComment())
                    .createdOn(commentDTO.getCreatedOn())
                    .updatedOn(commentDTO.getUpdatedOn())
                    .post(commentDTO.getPost())
                    .build();
    }

    public static CommentDTO maptoCommentDTO (Comment comment){
        return CommentDTO.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .createdOn(comment.getCreatedOn())
                    .updatedOn(comment.getUpdatedOn())
                    .post(comment.getPost())
                    .build();
    }

    public static CommentMessageDTO maptoMessageCommentDTO (CommentDTO commentDTO){
        return CommentMessageDTO.builder()
                    .comment(commentDTO.getComment())
                    .createdOn(commentDTO.getCreatedOn())
                    .updatedOn(commentDTO.getUpdatedOn())
                    .build();
    }
}
