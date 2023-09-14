package com.blogspot.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommentMessageDTO {
    private String comment;
    private int post_id;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
