package com.blogspot.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import com.blogspot.api.models.Roles;
import com.blogspot.api.models.Tags;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.RoleRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.TagService;
import com.blogspot.api.services.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    
    @Mock
    private PostRepository postRepo;

    @Mock
    private TagService tagService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private PostDTO postDTO;
    private Users user;
    private Roles role;
    private Tags tag1;
    private Tags tag2;

    @BeforeEach
    public void init(){
    //Arrange
    user = Users.builder()
                .id(1)
                .username("Test")
                .password("test")
                .build();
    post = Post.builder()
                .title("This is a test")
                .content("Test content")
                .tags(Arrays.asList(Tags.builder().tag("Test").build(), Tags.builder().tag("Testing").build()))
                .author(user)
                .build();
    postDTO = PostDTO.builder()
                    .title("This is a test")
                    .content("Test content")
                    .tags(Arrays.asList("Test", "Testing"))
                    .author_id(1)
                    .build();
    role = Roles.builder()
                .id(3)
                .title("AUTHOR")
                .build();
    tag1 = Tags.builder()
                .id(1)
                .tag("Test")
                .build();
    tag2 = Tags.builder()
                .id(2)
                .tag("Testing")
                .build();
    }   

    @Test
    public void PostService_CreatePost_ReturnPostDTO(){
        when(postRepo.save(Mockito.any(Post.class))).thenReturn(post);
        when(userRepo.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(roleRepo.findByTitle("AUTHOR")).thenReturn(Optional.ofNullable(role));
        when(tagService.findTag(tag1.getTag())).thenReturn(tag1);
        when(tagService.createTag(tag2.getTag())).thenReturn(tag2);

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
        
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));

        PostDTO savedPost = postService.getPost(1);

        Assertions.assertNotNull(savedPost);
    }

    @Test
    public void PostService_UpdatePost_ReturnPostDTO(){
        
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));
        when(postRepo.save(Mockito.any(Post.class))).thenReturn(post);
        when(tagService.findTag(tag1.getTag())).thenReturn(tag1);
        when(tagService.createTag(tag2.getTag())).thenReturn(tag2);

        PostDTO savedPost = postService.updatePost(1, postDTO, 1);

        Assertions.assertNotNull(savedPost);
    }

    @Test
    public void PostService_DeletePostById_ReturnPostDTO(){
        when(postRepo.findById(1)).thenReturn(Optional.ofNullable(post));

        assertAll(() -> {
            assertDoesNotThrow(() -> postService.deletePost(1, 1));
        });
    }
     
}
