package com.java.backend.blog.service.serviceImpl;

import com.java.backend.blog.dto.BlogRequest;
import com.java.backend.blog.entity.Blog;
import com.java.backend.blog.repository.BlogRepository;
import com.java.backend.blog.service.BlogService;
import com.java.backend.commons.exceptions.BlogAccessDeniedException;
import com.java.backend.commons.exceptions.BlogNotFoundException;
import com.java.backend.jwt.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Blog addBlog(User user, BlogRequest request) {
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .build();
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(String id, User user, BlogRequest request) throws IllegalAccessException {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));
        if(Objects.equals(blog.getAuthor().getId(), user.getId())){
            blog.setTitle(request.getTitle());
            blog.setContent(request.getContent());
        } else{
            throw new BlogAccessDeniedException(user.getId());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog findBlogById(String id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException(id));
    }

    @Override
    public List<Blog> getAll() {
        return blogRepository.findAll();
    }
}
