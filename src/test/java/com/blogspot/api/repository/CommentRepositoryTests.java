package com.blogspot.api.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blogspot.api.models.Comment;
import com.blogspot.api.repositories.CommentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CommentRepositoryTests {
    @Autowired
    private CommentRepository commentRepo;

    @Test
    public void CommentRepository_SaveAll_ReturnSavedComment(){
        Comment comment = Comment.builder()
                                .comment("This is a comment.")
                                .build();

        Comment savedCommment = commentRepo.save(comment);

        Assertions.assertNotNull(savedCommment);
        Assertions.assertTrue(savedCommment.getId()>0);
        Assertions.assertNotNull(savedCommment.getComment());
    }

    @Test
    public void CommentRepository_GetAll_ReturnAllComment(){
        Comment comment1 = Comment.builder()
                                .comment("This is a comment 1.")
                                .build();
        Comment comment2 = Comment.builder()
                                .comment("This is a comment 2.")
                                .build();

        commentRepo.save(comment1);
        commentRepo.save(comment2);

        List<Comment> comments = commentRepo.findAll();
        
        Assertions.assertNotNull(comments);
        Assertions.assertTrue(comments.size()>0);
        Assertions.assertEquals(comments.size(), 2);
    }

    @Test
    public void CommentRepository_GetSpecificComment_ReturnComment(){
        Comment comment1 = Comment.builder()
                                .comment("This is a comment 1.")
                                .build();

        commentRepo.save(comment1);

        Comment comment = commentRepo.findById(comment1.getId()).get();
        
        Assertions.assertNotNull(comment);
        Assertions.assertTrue(comment.getId()>0);
        Assertions.assertNotNull(comment.getComment());
    }

    @Test
    public void CommentRepository_UpdateComment_ReturnUpdateComment(){
        Comment comment1 = Comment.builder()
                                .comment("This is a comment 1.")
                                .build();

        commentRepo.save(comment1);

        Comment savedComment = commentRepo.findById(comment1.getId()).get();
        
        savedComment.setComment("Updated Comment.");

        Comment updatedComment = commentRepo.save(savedComment);

        Assertions.assertNotNull(updatedComment);
        Assertions.assertTrue(updatedComment.getId()>0);
        Assertions.assertNotNull(updatedComment.getComment());
    }

    @Test
    public void CommentRepository_DeleteComment_ReturnDeleteComment(){
        Comment comment1 = Comment.builder()
                                .comment("This is a comment 1.")
                                .build();

        commentRepo.save(comment1);
        commentRepo.deleteById(comment1.getId());

        Optional<Comment> deletedComment = commentRepo.findById(comment1.getId());

        Assertions.assertTrue(deletedComment.isEmpty());
    }
}
