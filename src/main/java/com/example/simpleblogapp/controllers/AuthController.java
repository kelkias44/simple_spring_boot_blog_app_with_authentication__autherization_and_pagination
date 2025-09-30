package com.example.simpleblogapp.controllers;

import com.example.simpleblogapp.model.User;
import com.example.simpleblogapp.model.requests.AuthRequest;
import com.example.simpleblogapp.model.response.AuthResponse;
import com.example.simpleblogapp.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) throws BadRequestException {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @RequestBody User user
    ){
        return ResponseEntity.ok(userService.add(user));
    }
}
