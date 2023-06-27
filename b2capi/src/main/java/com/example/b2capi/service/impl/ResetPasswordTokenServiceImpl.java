package com.example.b2capi.service.impl;

import com.example.b2capi.controller.base.BaseController;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.ResetPasswordToken;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.ResetPasswordTokenRepository;
import com.example.b2capi.service.IResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenServiceImpl extends BaseController implements IResetPasswordTokenService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Override
    public void createPasswordResetTokenForUser(User user, String token, LocalDateTime expiry) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user, expiry);
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    @Override
    public MessageResponse validateToken(String tok) {

        String result = isTokenAvailable(tok);

        if (result != null)
        {
            throw new IllegalArgumentException("Token Invalid");
        }
        else
        {
            return MessageResponse.builder().name(tok).build();
        }
    }

    public String isTokenAvailable(String tok) {
        ResetPasswordToken token = resetPasswordTokenRepository.findByToken(tok);
        String result = !isTokenFound(token) ? "invalidToken"
                : isTokenExpired(token) ? "expiredToken"
                : null;
        return result;
    }

    private boolean isTokenExpired(ResetPasswordToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

    private boolean isTokenFound(ResetPasswordToken token) {
        return token != null;
    }
}
