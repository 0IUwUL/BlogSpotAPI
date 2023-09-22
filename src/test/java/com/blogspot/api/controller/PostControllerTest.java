package com.blogspot.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.blogspot.api.controllers.PostController;
import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;
import com.blogspot.api.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;


@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDTO postDTO;

    @BeforeEach
    public void init(){
        postDTO = PostDTO.builder().title("This is another test").content("Test another content").build();
    }

    @Test
    public void PostController_CreatePost_ReturnCreate() throws Exception{
        given(postService.createPost(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/post/create")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(postDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(postDTO.getContent())));
    }

    @Test
    public void PostController_GetAllPost_ReturnResponseDTO() throws Exception{
        PostResponse responseDTO = PostResponse.builder()
                                                .content(Arrays.asList(postDTO))
                                                .pageNo(1)
                                                .pageSize(3)
                                                .totalElements(1)
                                                .totalPages(1)
                                                .last(false)
                                                .build();
                                                
        when(postService.getAllPost(1, 2)).thenReturn(responseDTO);
        ResultActions response = mockMvc.perform(get("/post/posts")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .param("pageNo", "1")
                                                .param("pageSize", "2"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDTO.getContent().size())));
                // .andDo(MockMvcResultHandlers.print());    
    }

    @Test
    public void PostController_GetPost_ReturnPostDTO() throws Exception{
        int postId = 1;
        
        when(postService.getPost(postId)).thenReturn(postDTO);
        ResultActions response = mockMvc.perform(get("/post/{id}", postId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(postDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(postDTO.getContent())));
    }

    @Test
    public void PostController_UpdatePost_ReturnPostDTO() throws Exception{
        int postId = 1;

        when(postService.updatePost(postId, postDTO)).thenReturn(postDTO);
        ResultActions response = mockMvc.perform(put("/post/update/{id}", postId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(postDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(postDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(postDTO.getContent())));
    }

    @Test
    public void PostController_DeletePost_ReturnString() throws Exception{
        int postId = 1;

        doNothing().when(postService).deletePost(postId);
        ResultActions response = mockMvc.perform(delete("/post/delete/{id}", postId));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
