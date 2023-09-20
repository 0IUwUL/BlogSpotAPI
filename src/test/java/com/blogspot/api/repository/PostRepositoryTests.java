package com.blogspot.api.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.PostRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepo;

    // Constructor with no arguments
    public PostRepositoryTests() {
    }

    @Test
    public void PostRepository_SaveAll_ReturnsSavedPost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a test")
                        .content("Test content")
                        .build();
        
        //Act
        Post savedPost = postRepo.save(post);

        //Assert
        Assertions.assertNotNull(savedPost);
        Assertions.assertTrue(savedPost.getId()>0);
        Assertions.assertNotNull(savedPost.getTitle());
        Assertions.assertNotNull(savedPost.getContent());
    }

    @Test
    public void PostRepository_ReturnAll_ReturnsAllPost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a 1st test")
                        .content("Test 1st content")
                        .build();
        Post post1 = Post.builder()
                        .title("This is a 2nd test")
                        .content("Test 2nd content")
                        .build();
        
        //Act
        postRepo.save(post);
        postRepo.save(post1);

        List<Post> savedPost = postRepo.findAll();

        //Assert
        Assertions.assertNotNull(savedPost);
        Assertions.assertEquals(savedPost.size(), 2);
    }

    @Test
    public void PostRepository_ReturnPost_ReturnsSpecificPost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a 1st test")
                        .content("Test 1st content")
                        .build();
        
        //Act
        postRepo.save(post);

        Post savedPost = postRepo.findById(post.getId()).get();

        //Assert
        Assertions.assertNotNull(savedPost);
        Assertions.assertTrue(savedPost.getId()>0);
        Assertions.assertNotNull(savedPost.getTitle());
        Assertions.assertNotNull(savedPost.getContent());
    }

    @Test
    public void PostRepository_ReturnPosts_ReturnsSimilarTitlePost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a 1st test")
                        .content("Test 1st content")
                        .build();
        Post post1 = Post.builder()
                        .title("This is another test. 2nd")
                        .content("Test 2nd content")
                        .build();
        
        //Act
        postRepo.save(post);
        postRepo.save(post1);

        List<Post> savedPost = postRepo.searchPost("test");

        //Assert
        Assertions.assertNotNull(savedPost);
        Assertions.assertTrue(savedPost.size()>0);
        Assertions.assertEquals(savedPost.size(), 2);
    }

    @Test
    public void PostRepository_UpdatePost_UpdatePost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a 1st test")
                        .content("Test 1st content")
                        .build();
        
        //Act
        postRepo.save(post);

        Post savedPost = postRepo.findById(post.getId()).get();
        
        savedPost.setTitle("Updated Title");
        savedPost.setContent("Updated Content");

        Post updatedPost = postRepo.save(savedPost);

        //Assert
        Assertions.assertNotNull(updatedPost);
        Assertions.assertTrue(updatedPost.getId()>0);
        Assertions.assertEquals(updatedPost.getId(), savedPost.getId());
        Assertions.assertNotNull(updatedPost.getTitle());
        Assertions.assertNotNull(updatedPost.getContent());
    }

    @Test
    public void PostRepository_DeletePost_DeletePost(){
        //Arrange
        Post post = Post.builder()
                        .title("This is a 1st test")
                        .content("Test 1st content")
                        .build();
        
        //Act
        postRepo.save(post);
        postRepo.deleteById(post.getId());

        Optional<Post> deletedPost = postRepo.findById(post.getId());

        //Assert
        Assertions.assertTrue(deletedPost.isEmpty());
    }
}
