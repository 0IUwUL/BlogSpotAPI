package com.blogspot.api.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogspot.api.models.Tags;
import com.blogspot.api.repositories.TagRepository;
import com.blogspot.api.services.TagService;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepo;

    @Override
    public Tags createTag(String tag) {
        return tagRepo.save(Tags.builder().tag(tag).build());
    }

    @Override
    public Tags findTag(String searchTag) {
        return tagRepo.findByGivenTag(searchTag);
    }
    
}
