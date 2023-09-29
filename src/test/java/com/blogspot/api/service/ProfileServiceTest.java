package com.blogspot.api.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.ProfileDTO;
import com.blogspot.api.models.Post;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.impl.ProfileServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    private PostRepository postRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Post post;
    private Post test_post;
    private Users user;
    private Users author;
    private List<Post> favorites;
    private List<Post> posts;

    @BeforeEach
    public void init(){
        user = Users.builder()
                    .id(1)
                    .username("Test User")
                    .build();
        author = Users.builder()
                    .id(2)
                    .username("Test User")
                    .build();
        post = Post.builder()
                    .author(author)
                    .title("Test Title")
                    .content("Test content")
                    .build();
        test_post = Post.builder()
                    .author(user)
                    .title("Test User")
                    .content("Test content User")
                    .build();
        favorites = Arrays.asList(post);
        posts = Arrays.asList(test_post);
    }

    @Test
    public void ProfileService_AddFavoritePost_ReturnString(){
        int postId = 1;
        int userId = 1;
        when(postRepo.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(user));

        String message = profileService.addFavoritePost(userId, postId);
        Assertions.assertNotNull(message);
        Assertions.assertEquals(message, "Added to favorites");
    }

    @Test
    public void ProfileService_AddFollower_ReturnsString(){
        int userId = 1;
        int authorId = 2;

        when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(userRepo.findById(authorId)).thenReturn(Optional.ofNullable(author));

        String message = profileService.addFollower(userId, authorId);
        Assertions.assertNotNull(message);
        Assertions.assertEquals(message, "Added to following");
    }

    @Test
    public void ProfileService_GetFavorites_ReturnListOfPosts(){
        int userId = 1;

        when(postRepo.findByUsersIdOrderByCreatedOnDesc(userId)).thenReturn(favorites);
        List<PostDTO> favorites = profileService.getFavorites(userId);

        Assertions.assertNotNull(favorites);
    }

    @Test
    public void ProfileService_GetProfile_ReturnProfileDTO(){
        int userId = 1; 
        int follower = 1;
        when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(postRepo.findByUsersIdOrderByCreatedOnDesc(userId)).thenReturn(favorites);
        when(userRepo.countByFollowersId(userId)).thenReturn(follower);
        when(postRepo.findAllByAuthorId(userId)).thenReturn(posts);
        
        ProfileDTO profile = profileService.getProfile(userId);

        Assertions.assertNotNull(profile);
        Assertions.assertNotNull(profile.getFollower_count());
        Assertions.assertNotNull(profile.getFavorites());
        Assertions.assertNotNull(profile.getPosts());
        Assertions.assertNotNull(profile.getUsername());
    }
}
