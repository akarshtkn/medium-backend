package com.java.backend.blog.service;

import com.java.backend.blog.dto.BlogRequest;
import com.java.backend.blog.entity.Blog;
import com.java.backend.commons.exceptions.BlogAccessDeniedException;
import com.java.backend.jwt.entity.User;

import java.util.List;

public interface BlogService {
    Blog addBlog(User user, BlogRequest request);

    Blog updateBlog(String id, User user, BlogRequest request) throws IllegalAccessException;

    Blog findBlogById(String id);

    List<Blog> getAll();
}
