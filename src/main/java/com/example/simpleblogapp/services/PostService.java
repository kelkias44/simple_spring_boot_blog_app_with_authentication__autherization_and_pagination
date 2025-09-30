package com.example.simpleblogapp.services;

import com.example.simpleblogapp.config.JwtService;
import com.example.simpleblogapp.model.Post;
import com.example.simpleblogapp.model.User;
import com.example.simpleblogapp.repositories.PostRepository;
import com.example.simpleblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public Page<Post> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    public Post getById(Long id){
        return postRepository.findById(id).orElseThrow();
    }

    public Post add(Post post, String token){
        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        post.setAuthorName(user.getName());
        post.setAuthorUserId(user.getId());

        return postRepository.save(post);
    }

    public Post update(Long id, Post post, String token){
        postRepository.findById(id).orElseThrow();
        post.setId(id);

        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        post.setAuthorName(user.getName());
        post.setAuthorUserId(user.getId());

        return postRepository.save(post);
    }


    public Boolean delete(Long id, Post post){
        postRepository.findById(id).orElseThrow();
        post.setId(id);

        postRepository.delete(post);

        return true;
    }
}
