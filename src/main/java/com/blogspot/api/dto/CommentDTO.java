package com.blogspot.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommentDTO {
    private int id;
    private String comment;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
