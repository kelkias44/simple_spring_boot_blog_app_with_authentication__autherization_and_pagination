package com.example.simpleblogapp.repositories;

import com.example.simpleblogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
