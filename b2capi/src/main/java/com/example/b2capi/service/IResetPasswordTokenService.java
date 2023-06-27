package com.example.b2capi.service;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.dto.message.BaseMessage;
import com.example.b2capi.domain.dto.message.ExtendedMessage;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.ResetPasswordToken;
import com.example.b2capi.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface IResetPasswordTokenService {
    void createPasswordResetTokenForUser(User user, String token, LocalDateTime expiry);

    MessageResponse validateToken(String token);

    String isTokenAvailable(String tok);
}
