package com.blogspot.api.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogspot.api.dto.PostDTO;
import com.blogspot.api.dto.ProfileDTO;
import com.blogspot.api.exceptions.PostException;
import com.blogspot.api.exceptions.UserException;
import com.blogspot.api.models.Post;
import com.blogspot.api.models.Users;
import com.blogspot.api.repositories.PostRepository;
import com.blogspot.api.repositories.UserRepository;
import com.blogspot.api.services.ProfileService;

import static com.blogspot.api.mapper.PostMapper.maptoPostDTO;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public String addFavoritePost(int user_id, int post_id) {
        Post post = postRepo.findById(post_id).orElseThrow(() -> new PostException("Post not found."));
        Users user = userRepo.findById(user_id).orElseThrow(() -> new UserException("User not found."));
        String message;
        if(post.getAuthor().getId() == user.getId())
            throw new PostException("You may not favorite your own post.");

        //get list of favorites from user
        List<Post> favorites = user.getFavorites();
        if(!favorites.contains(post)){
            favorites.add(post);
            message = "Added to favorites";
        }else{
            favorites.remove(post);
            message = "Removed from favorites";
        }
        user.setFavorites(favorites);
        userRepo.save(user);
        return message;
    }

    @Override
    public String addFollower(int user_id, int author_id) {
        if(user_id == author_id)
            throw new UserException("User not found.");

        String message;
        Users user = userRepo.findById(user_id).orElseThrow(()-> new UserException("User not found."));
        Users author = userRepo.findById(author_id).orElseThrow(()-> new UserException("Author not found."));
        
        //get list of following from user
        List<Users> authors = user.getFollowing();
        if(!authors.contains(author)){
            authors.add(author);
            message = "Added to following";
        }else{
            authors.remove(author);
            message = "Removed from following";
        }
        user.setFollowing(authors);
        userRepo.save(user);
        return message;
    }

    @Override
    public List<PostDTO> getFavorites(int id) {
        List<Post> posts = postRepo.findByUsersIdOrderByCreatedOnDesc(id);
        List<PostDTO> favorites = posts.stream().map((post)-> maptoPostDTO(post)).collect(Collectors.toList());
        
        return favorites;
    }

    @Override
    public ProfileDTO getProfile(int id) {
        ProfileDTO user = new ProfileDTO();
        user.setUsername(userRepo.findById(id).get().getUsername());
        user.setFavorites(postRepo.findByUsersIdOrderByCreatedOnDesc(id).stream().map((post)-> maptoPostDTO(post)).collect(Collectors.toList()));
        user.setFollower_count(userRepo.countByFollowersId(id));
        user.setPosts(postRepo.findAllByAuthorId(id).stream().map((post)->maptoPostDTO(post)).collect(Collectors.toList()));
        return user;
    }
}
