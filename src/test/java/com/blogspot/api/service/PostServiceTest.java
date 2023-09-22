package com.blogspot.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    
    @Mock
    private PostRepository postRepo;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    public void PostService_CreatePost_ReturnPostDTO(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a test")
                        .content("Test content")
                        .build();
        PostDTO postDTO = PostDTO.builder()
                                .title("This is a test")
                                .content("Test content")
                                .build();
        
        when(postRepo.save(Mockito.any(Post.class))).thenReturn(post);

        PostDTO savedPost = postService.createPost(postDTO);

        Assertions.assertNotNull(savedPost);
    }

    @Test
    public void PostService_GetAllPost_ReturnsResponseDTO(){
        Page<Post> posts = Mockito.mock(Page.class);

        when(postRepo.findAllByOrderByCreatedOnDesc(Mockito.any(Pageable.class))).thenReturn(posts);

        PostResponse savePost = postService.getAllPost(1, 2);

        Assertions.assertNotNull(savePost);
    }

    @Test
    public void PostService_GetPostById_ReturnPostDTO(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a test")
                        .content("Test content")
                        .build();
        
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));

        PostDTO savedPost = postService.getPost(1);

        Assertions.assertNotNull(savedPost);
    }

    @Test
    public void PostService_UpdatePost_ReturnPostDTO(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a test")
                        .content("Test content")
                        .build();
        PostDTO postDTO = PostDTO.builder()
                                .title("This is an updated test")
                                .content("Test an updated content")
                                .build();
        
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));
        when(postRepo.save(Mockito.any(Post.class))).thenReturn(post);

        PostDTO savedPost = postService.updatePost(1, postDTO);

        Assertions.assertNotNull(savedPost);
    }

    @Test
    public void PostService_DeletePostById_ReturnPostDTO(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a test")
                        .content("Test content")
                        .build();
        
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));

        assertAll(() -> postService.deletePost(1));
    }
     
}
