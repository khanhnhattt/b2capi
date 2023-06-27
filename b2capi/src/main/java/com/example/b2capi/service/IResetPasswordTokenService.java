package com.example.b2capi.service;

import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.User;

import java.time.LocalDateTime;

public interface IResetPasswordTokenService {
    void createPasswordResetTokenForUser(User user, String token, LocalDateTime expiry);

    MessageResponse validateToken(String token);

    String isTokenAvailable(String tok);
}
