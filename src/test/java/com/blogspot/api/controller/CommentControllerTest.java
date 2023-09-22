package com.blogspot.api.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.blogspot.api.controllers.CommentController;
import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentDTO commentDTO;

    @BeforeEach
    public void init(){
        commentDTO = CommentDTO.builder().comment("This is another comment.").build();
    }

    @Test
    public void CommentController_CreateComment_ReturnCreate() throws Exception{
        int postId = 1;

        when(commentService.createComment(postId, commentDTO)).thenReturn(commentDTO);
        ResultActions response = mockMvc.perform(post("/comment/{id}/create", postId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(commentDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", CoreMatchers.is(commentDTO.getComment())));
    }

    @Test
    public void CommentController_GetComments_ReturnListCommentsDTO() throws Exception{
        int postId = 1;

        when(commentService.getAllComments(postId)).thenReturn(Arrays.asList(commentDTO));
        ResultActions response = mockMvc.perform(get("/comment/{postId}", postId)
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(commentDTO).size())));
    }

    @Test
    public void CommentController_GetComment_ReturnCommnetDTO() throws Exception{
        int postId = 1;
        int commentId = 1;

        when(commentService.getComment(postId, commentId)).thenReturn(commentDTO);
        ResultActions response = mockMvc.perform(get("/comment/{postId}/{commentId}", postId, commentId)
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", CoreMatchers.is(commentDTO.getComment())));
    }

    @Test
    public void CommentController_UpdateComment_ReturnCommentDTO() throws Exception{
        int postId = 1;
        int commentId = 1;

        when(commentService.updateComment(postId, commentId, commentDTO)).thenReturn(commentDTO);
        ResultActions response = mockMvc.perform(put("/comment/{postId}/{id}", postId, commentId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(commentDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", CoreMatchers.is(commentDTO.getComment())));
    }

    @Test
    public void CommentController_DeleteComment_ReturnVoid() throws Exception{
        int postId = 1;
        int commentId = 1;

        doNothing().when(commentService).deleteComment(postId, commentId);
        ResultActions response = mockMvc.perform(delete("/comment/{postId}/{commentId}", postId, commentId)
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
