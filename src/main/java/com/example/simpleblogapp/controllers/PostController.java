package com.example.simpleblogapp.controllers;

import com.example.simpleblogapp.model.Post;
import com.example.simpleblogapp.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/post")
public class PostController {
    @Autowired
    private PostService postService;


    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(postService.getAll(page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping
    public ResponseEntity<Post> addPost(
            @RequestBody Post post,
            HttpServletRequest request
    ){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok(postService.add(post, token));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestBody Post post,
            HttpServletRequest request
    ){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok(postService.update(id, post, token));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePost(
            @PathVariable Long id,
            @RequestBody Post post
    ){
        return ResponseEntity.ok(postService.delete(id, post));
    }
}
