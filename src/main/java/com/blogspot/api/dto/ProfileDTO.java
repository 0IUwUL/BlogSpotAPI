package com.blogspot.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProfileDTO {
    private String username;
    private List<PostDTO> posts;
    private Integer follower_count;
    private List<PostDTO> favorites;
}
