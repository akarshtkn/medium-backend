package com.java.backend.blog.controller;

import com.java.backend.blog.dto.BlogRequest;
import com.java.backend.blog.entity.Blog;
import com.java.backend.blog.service.BlogService;
import com.java.backend.jwt.entity.User;
import com.java.backend.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("api/v1")
@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final AuthenticationService userService;

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @PostMapping("/blog")
    public ResponseEntity<Blog> addBlog(@RequestBody BlogRequest request){
        logger.info("Executing add blog endpoint");

        User user = userService.findUser(request.getId());
        Blog blog = blogService.addBlog(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(blog);
    }

    @PutMapping("/blog")
    public ResponseEntity<Blog> updateBlog(@RequestBody BlogRequest request,
                                             @RequestParam String id) throws IllegalAccessException {
        logger.info("Executing update blog endpoint");

        User user = userService.findUser(request.getId());
        Blog blog = blogService.updateBlog(id, user, request);
        return ResponseEntity.status(HttpStatus.OK).body(blog);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable String id){
        logger.info("Executing get blog endpoint");

        Blog blog = blogService.findBlogById(id);
        return ResponseEntity.status(HttpStatus.OK).body(blog);
    }

    @GetMapping("/blog/bulk")
    public ResponseEntity<List<Blog>> getAllBlogs(){
        logger.info("Executing blog bulk endpoint");

        List<Blog> blogs = blogService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(blogs);
    }
}