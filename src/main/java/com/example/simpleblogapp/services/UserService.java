package com.example.simpleblogapp.services;

import com.example.simpleblogapp.config.JwtService;
import com.example.simpleblogapp.exception.ResourceNotFoundException;
import com.example.simpleblogapp.model.User;
import com.example.simpleblogapp.model.requests.AuthRequest;
import com.example.simpleblogapp.model.response.AuthResponse;
import com.example.simpleblogapp.repositories.UserRepository;
import com.example.simpleblogapp.utils.enums.Role;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return userRepository.findAll().stream().toList();
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public AuthResponse add(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email Already Exists!!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse addAdmin(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email Already Exists!!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse authenticate(AuthRequest request) throws BadRequestException {
        User savedUser = userRepository.findByEmail(request.getEmail()).orElseThrow( () ->
                new ResourceNotFoundException("User with this email doesn't exist!!")
        );
        if(passwordEncoder.matches(request.getPassword(), savedUser.getPassword())){
            User user = new User();
            user.setId(savedUser.getId());
            user.setEmail(savedUser.getEmail());
            user.setName(savedUser.getName());
            user.setRole(savedUser.getRole());

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return new AuthResponse(user,accessToken,refreshToken);
        }else{
            throw new IllegalArgumentException("Wrong Password!!");
        }

    }

    public User update(Long id,User user){
        userRepository.findById(id).orElseThrow();
        user.setId(id);

        return userRepository.save(user);
    }

    public Boolean delete(Long id, User user){
        userRepository.findById(id).orElseThrow();
        user.setId(id);

        userRepository.delete(user);
        return true;
    }


}
