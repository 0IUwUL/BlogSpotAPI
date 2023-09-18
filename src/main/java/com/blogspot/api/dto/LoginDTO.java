package com.blogspot.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class LoginDTO {
    private String username;
    private String password;
    private LocalDateTime accessOn;
}
