package com.blogspot.api.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.services.PostService;

import static com.blogspot.api.mapper.PostMapper.maptoPost;
import static com.blogspot.api.mapper.PostMapper.maptoPostDTO;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = maptoPost(postDTO);
        Post newPost = postRepo.save(post);
        PostDTO postResponse = maptoPostDTO(newPost);
        return postResponse;
    }


    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRepo.findAllByOrderByUpdatedOn();
        return posts.stream().map((post)-> maptoPostDTO(post)).collect(Collectors.toList());
    }


    @Override
    public PostDTO getPost(int id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new PostException("Post does not exist."));
        return maptoPostDTO(post);
    }


    @Override
    public PostDTO updatePost(int id, PostDTO data) {
        Post post = postRepo.findById(id).orElseThrow(()-> new PostException("Post could not be updated."));
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setCreatedOn(post.getCreatedOn());
        post.setUpdatedOn(LocalDateTime.now());

        Post updated_post = postRepo.save(post);

        return maptoPostDTO(updated_post);
    }

    @Override
    public void deletePost(int id) {
        postRepo.findById(id).orElseThrow(()-> new PostException("Post does not exist."));
        postRepo.deleteById(id);
    }
    
}
