package com.blogspot.api.services.impl;

import org.springframework.stereotype.Service;

import com.blogspot.api.dto.CommentDTO;
import com.blogspot.api.dto.CommentMessageDTO;
import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.models.Comment;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.CommentRepository;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.CommentService;

import static com.blogspot.api.mapper.CommentMapper.maptoCommentDTO;
import static com.blogspot.api.mapper.CommentMapper.maptoComment;
import static com.blogspot.api.mapper.CommentMapper.maptoMessageCommentDTO;;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentMessageDTO createComment(int id, CommentDTO commentDTO) {
        Comment comment = maptoComment(commentDTO);
        Post post = postRepo.findById(id).orElseThrow(()-> new PostException("Post does not exist."));
        comment.setPost(post);
        CommentDTO save = maptoCommentDTO(commentRepo.save(comment));
        CommentMessageDTO result = maptoMessageCommentDTO(save);
        result.setPost_id(save.getPost().getId());
        return result;
    }
    
}
