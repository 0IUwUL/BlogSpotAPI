package com.blogspot.api.services;

import com.blogspot.api.models.Tags;

public interface TagService {
    Tags createTag(String tag);
    Tags findTag(String tag);
}
