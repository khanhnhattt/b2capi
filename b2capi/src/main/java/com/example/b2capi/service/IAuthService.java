package com.example.b2capi.service;


import com.example.b2capi.domain.dto.auth.JwtResponse;
import com.example.b2capi.domain.dto.auth.LoginDTO;
import com.example.b2capi.domain.dto.auth.RegisterDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.User;
import jakarta.mail.internet.AddressException;

import java.util.List;

public interface IAuthService {
    MessageResponse addUser(RegisterDTO registerDto);

    User findUserByEmail(String email);

    List<RegisterDTO> findAllUsers();

    JwtResponse login(LoginDTO loginDto);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    MessageResponse resetPasswordByEmail(String email) throws AddressException;
}
