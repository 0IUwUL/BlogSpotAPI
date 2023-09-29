package com.blogspot.api.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.blogspot.api.models.Post;
import com.blogspot.api.models.Tags;
import com.blogspot.api.repositories.PostRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepo;

    private Post post;
    private Post post1;

    @BeforeEach
    public void init(){
        post = Post.builder()
                    .title("This is a test")
                    .content("Test content")
                    .build();
        post1 = Post.builder()
                    .title("This is a 2nd test")
                    .content("Test 2nd content")
                    .tags(Arrays.asList(Tags.builder().tag("Test2").build(), Tags.builder().tag("Testing2").build()))
                    .build();
    }

    @Test
    public void PostRepository_SaveAll_ReturnsSavedPost(){
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
        //Act
        postRepo.save(post);
        postRepo.save(post1);
        Pageable pageable = PageRequest.of(0, 5);

        Page<Post> pagePost = postRepo.searchContext(pageable, "test", Arrays.asList("Test2"));
        List<Post> savedPosts = pagePost.getContent();
        //Assert
        Assertions.assertNotNull(savedPosts);
        Assertions.assertTrue(savedPosts.size()>0);
        Assertions.assertEquals(savedPosts.size(), 2);
    }

    @Test
    public void PostRepository_UpdatePost_ReturnUpdatedPost(){
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
        //Act
        postRepo.save(post);
        postRepo.deleteById(post.getId());

        Optional<Post> deletedPost = postRepo.findById(post.getId());

        //Assert
        Assertions.assertTrue(deletedPost.isEmpty());
    }
}
