package com.blogspot.api.services.impl;

import org.springframework.stereotype.Service;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.exceptions.CommentException;
import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.models.Comment;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.CommentRepository;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.CommentService;

import static com.blogspot.api.mapper.CommentMapper.maptoCommentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.blogspot.api.mapper.CommentMapper.maptoComment;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDTO createComment(int id, CommentDTO commentDTO) {
        Comment comment = maptoComment(commentDTO);
        Post post = postRepo.findById(id).orElseThrow(()-> new PostException("Post does not exist."));
        comment.setPost(post);
        CommentDTO save = maptoCommentDTO(commentRepo.save(comment));
        return save;
    }

    @Override
    public List<CommentDTO> getAllComments(int id) {
        List<Comment> result = commentRepo.findAllByPostIdOrderByCreatedOnDesc(id);
        return result.stream().map((comment) -> maptoCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getComment(int post_id, int id) {
        if(compareId(post_id, id))
            throw new CommentException("Incorrect assignment");

        Comment comment = commentRepo.findById(id).orElseThrow(() -> new CommentException("Comment does not exist."));
        return maptoCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(int post_id, int id, CommentDTO commentDTO) {
        if(compareId(post_id, id))
            throw new CommentException("Incorrect assignment");
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new CommentException("Comment does not exist."));
        comment.setComment(commentDTO.getComment());
        comment.setUpdatedOn(LocalDateTime.now());
        CommentDTO result = maptoCommentDTO(commentRepo.save(comment));
        return result;
    }

     @Override
    public void deleteComment(int post_id, int id) {
        if(compareId(post_id, id))
            throw new CommentException("Incorrect assignment");
        commentRepo.deleteById(id);
    }

    private boolean compareId(int post_id, int id){
        Post post = postRepo.findById(post_id).orElseThrow(() -> new PostException("Post does not exist."));
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new CommentException("Comment does not exist."));
        if(comment.getPost().getId() != post.getId())
            return true;
        return false;
    }
    
}
