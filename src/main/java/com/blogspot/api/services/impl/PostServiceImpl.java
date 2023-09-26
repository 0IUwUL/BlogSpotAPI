package com.blogspot.api.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.blogspot.api.models.Roles;
import com.blogspot.api.models.Tags;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.RoleRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.PostService;
import com.blogspot.api.services.TagService;

import static com.blogspot.api.mapper.PostMapper.maptoPost;
import static com.blogspot.api.mapper.PostMapper.maptoPostDTO;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepo;
    private UserRepository userRepo;
    private RoleRepository roleRepo;

    private TagService tagService;

    public PostServiceImpl(PostRepository postRepo, UserRepository userRepo, TagService tagService,
                            RoleRepository roleRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.tagService = tagService;
        this.roleRepo = roleRepo;
    }


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = maptoPost(postDTO);

        if(postDTO.getTags().size() > 0){
            post.setTags(postDTO.getTags().stream().map((tag)-> searchTag(tag)).collect(Collectors.toList()));
        }
        Users user = userRepo.findById(postDTO.getAuthor_id()).orElseThrow(()-> new UserException("User does not exist."));

        post.setAuthor(user);
        Post newPost = postRepo.save(post);
        PostDTO postResponse = maptoPostDTO(newPost);
        
        Roles role = roleRepo.findByTitle("AUTHOR").get();

        //get pre existing role for user
        List<Roles> userRoles = user.getRoles();
        if(!userRoles.contains(role)){
            userRoles.add(role);

            // Update the roles for the user
            user.setRoles(userRoles);
            userRepo.save(user);
        }
        return postResponse;
    }

    private Tags searchTag(String searchTag) {
        Tags tag = tagService.findTag(searchTag);
        if(tag != null)
            return tag;
        return tagService.createTag(searchTag);
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

        if(data.getTags().size() > 0){
            post.setTags(data.getTags().stream().map((tag)-> searchTag(tag)).collect(Collectors.toList()));
        }

        post.setCreatedOn(post.getCreatedOn());
        post.setUpdatedOn(LocalDateTime.now());

        Post updated_post = postRepo.save(post);

        return maptoPostDTO(updated_post);
    }

    @Override
    public void deletePost(int id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new PostException("Post does not exist."));
        // Create a modifiable copy of the tags collection
        List<Tags> tags = new ArrayList<>(post.getTags());
        
        // Clear the tags
        tags.clear();
        
        // Set the modified tags back to the post
        post.setTags(tags);
        
        // Now you can safely clear the tags in the post
        postRepo.deleteById(id);
    }
    
}
