package com.example.simpleblogapp.controllers;

import com.example.simpleblogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {


    @Autowired

    private PostRepository postRepository;




    @GetMapping("/")

    public String home(Model model) {

        model.addAttribute("posts", postRepository.findAll());

        return "home";

    }

}
