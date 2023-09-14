package com.blogspot.api.dto;

import java.time.LocalDateTime;

import com.blogspot.api.models.Post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommentDTO {
    private int id;
    private String comment;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Post post;
}
