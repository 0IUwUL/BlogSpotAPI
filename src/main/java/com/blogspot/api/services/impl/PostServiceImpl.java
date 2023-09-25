package com.blogspot.api.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.PostResponse;
import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.exceptions.UserException;
import com.blogspot.api.models.Post;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.PostService;

import static com.blogspot.api.mapper.PostMapper.maptoPost;
import static com.blogspot.api.mapper.PostMapper.maptoPostDTO;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepo;
    private UserRepository userRepo;

    public PostServiceImpl(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = maptoPost(postDTO);
        post.setAuthor(userRepo.findById(postDTO.getAuthor_id()).orElseThrow(()-> new UserException("User does not exist.")));
        Post newPost = postRepo.save(post);
        PostDTO postResponse = maptoPostDTO(newPost);
        return postResponse;
    }


    @Override
    public PostResponse getAllPost(int pageNo, int pageSize) {
        Pageable pagable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepo.findAllByOrderByCreatedOnDesc(pagable);
        List<Post> listofPosts = posts.getContent();
        List<PostDTO> content = listofPosts.stream().map((post)-> maptoPostDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        
        return postResponse;
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
