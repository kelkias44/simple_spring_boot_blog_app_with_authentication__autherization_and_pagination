package com.example.simpleblogapp.model.response;

import com.example.simpleblogapp.model.User;

public record AuthResponse(User user, String accessToken, String refreshToken) {
}
