package com.example.b2capi.service.impl;

import com.example.b2capi.domain.model.ResetPasswordToken;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.ResetPasswordTokenRepository;
import com.example.b2capi.service.ResetPasswordTokenService;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user);
        resetPasswordTokenRepository.save(resetPasswordToken);
    }
}
