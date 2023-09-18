package com.blogspot.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class RegisterDTO {
    private String username;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
