package com.example.b2capi.service;


import com.example.b2capi.domain.dto.LoginDTO;
import com.example.b2capi.domain.dto.RegisterDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.User;

import java.util.List;

public interface IAuthService {
    MessageResponse addUser(RegisterDTO registerDto);

    User findUserByEmail(String email);

    List<RegisterDTO> findAllUsers();

    MessageResponse login(LoginDTO loginDto);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
