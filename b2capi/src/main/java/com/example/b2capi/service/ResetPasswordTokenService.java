package com.example.b2capi.service;

import com.example.b2capi.domain.model.User;

public interface ResetPasswordTokenService {
    void createPasswordResetTokenForUser(User user, String token);
}
