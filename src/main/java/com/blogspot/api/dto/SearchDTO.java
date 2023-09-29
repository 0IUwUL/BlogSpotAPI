package com.blogspot.api.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SearchDTO {
    @Nullable
    private String title;
    @Nullable
    private List<String> tags;
    // @Nullable
    // private String start_date;
    // @Nullable
    // private String end_date;
}
