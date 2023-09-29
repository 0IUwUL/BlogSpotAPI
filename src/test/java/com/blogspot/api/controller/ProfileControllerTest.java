package com.blogspot.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;

import com.blogspot.api.controllers.ProfileController;
import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.ProfileDTO;
import com.blogspot.api.services.ProfileService;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    public void PostController_addFavoritePost_ReturnsString() throws Exception{
        when(profileService.addFollower(1, 2)).thenReturn("Added to favorites");

        ResultActions response = mockMvc.perform(get("/api/user/favorite/{user_id}/post/{post_id}", 1, 2)
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
                // .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostController_FollowAuthor_ReturnsString() throws Exception{
        when(profileService.addFollower(1, 2)).thenReturn("Added to following");

        ResultActions response = mockMvc.perform(get("/api/user/follow/{user_id}/{author_id}", 1, 2)
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
                // .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostController_GetAllFavorite_ReturnsListofPostDTO() throws Exception{
        
        PostDTO post = PostDTO.builder()
                    .author_id(2)
                    .title("Test Title")
                    .content("Test content")
                    .build();

        List<PostDTO> favorites = Arrays.asList(post);

        when(profileService.getFavorites(1)).thenReturn(favorites);

        ResultActions response = mockMvc.perform(get("/api/user/favorites/{user_id}", 1)
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(favorites.size())));
                // .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PostController_GetProfile_ReturnsProfileDTO() throws Exception{
        
        ProfileDTO profile = ProfileDTO.builder()
                                        .username("Test User")
                                        .posts(Arrays.asList(PostDTO.builder().build()))
                                        .favorites(Arrays.asList(PostDTO.builder().build()))
                                        .follower_count(1)
                                        .build();
        
        when(profileService.getProfile(1)).thenReturn(profile);

        ResultActions response = mockMvc.perform(get("/api/user/profile/{user_id}", 1)
                                        .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(profile.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.posts.size()", CoreMatchers.is(profile.getPosts().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.follower_count", CoreMatchers.is(profile.getFollower_count())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.favorites.size()", CoreMatchers.is(profile.getFavorites().size())));
        //         // .andDo(MockMvcResultHandlers.print());
    }
}
