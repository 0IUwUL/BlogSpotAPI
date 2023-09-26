package com.blogspot.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.models.Comment;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.CommentRepository;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.impl.CommentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepo;
    @Mock
    private PostRepository postRepo;

    @InjectMocks
    private CommentServiceImpl commentService;
    
    private Post post;
    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    public void init(){
        post = Post.builder().id(1).title("This is a test").content("Test content").build();
        comment = Comment.builder().id(1).comment("This is a comment.").post(post).build();
        commentDTO = CommentDTO.builder().comment("This is another comment.").build();
    }

    @Test
    public void CommentService_CreateComment_ReturnCommentDTO(){
        when(postRepo.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        when(commentRepo.save(Mockito.any(Comment.class))).thenReturn(comment);

        CommentDTO savedComment = commentService.createComment(post.getId(), commentDTO);

        Assertions.assertNotNull(savedComment);
        Assertions.assertTrue(savedComment.getId()>0);
        Assertions.assertNotNull(savedComment.getComment());
    }

    @Test
    public void CommentService_GetAllCommentByPostId_ReturnListCommentDTO(){
        int commentId = 1;
        when(commentRepo.findAllByPostIdOrderByCreatedOnDesc(commentId)).thenReturn(Arrays.asList(comment));
        
        List<CommentDTO> commentReturn = commentService.getAllComments(commentId);

        Assertions.assertNotNull(commentReturn);
    }

    @Test
    public void CommentService_GetCommentById_ReturnCommentDTO(){
        int commentId = 1;
        int postId = 1;
        when(postRepo.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        when(commentRepo.findById(comment.getId())).thenReturn(Optional.ofNullable(comment));
        
        CommentDTO commentReturn = commentService.getComment(postId, commentId);

        Assertions.assertNotNull(commentReturn);
    }

    @Test
    public void CommentService_UpdateComment_ReturnCommentDTO(){
        int postId = 1;
        int commentId = 1;

        post.setComments(Arrays.asList(comment));
        comment.setPost(post);

        when(postRepo.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(commentRepo.findById(commentId)).thenReturn(Optional.ofNullable(comment));
        
        when(commentRepo.save(comment)).thenReturn(comment);

        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);

        Assertions.assertNotNull(updatedComment);
        Assertions.assertNotNull(updatedComment.getComment());
    }

    @Test
    public void CommentService_DeleteComment_ReturnVoid(){
        int postId = 1;
        int commentId = 1;

        post.setComments(Arrays.asList(comment));
        comment.setPost(post);

        when(postRepo.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(commentRepo.findById(commentId)).thenReturn(Optional.ofNullable(comment));

        assertAll(()->commentService.deleteComment(postId, commentId));

    }
}
