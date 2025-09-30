package com.example.simpleblogapp.controllers;

import com.example.simpleblogapp.model.User;
import com.example.simpleblogapp.model.requests.AuthRequest;
import com.example.simpleblogapp.model.response.AuthResponse;
import com.example.simpleblogapp.services.UserService;
import com.example.simpleblogapp.utils.enums.Role;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody User user) {
        return ResponseEntity.ok(userService.addAdmin(user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(userService.getById(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user
            ){
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(
            @PathVariable Long id,
            @RequestBody User user
    ){
        return ResponseEntity.ok(userService.delete(id,user));
    }

}
