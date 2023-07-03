package com.example.b2capi.service;


import com.example.b2capi.domain.dto.auth.*;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.User;
import jakarta.mail.internet.AddressException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthService {
    RegisterSuccessDTO addUser(RegisterDTO registerDto);

    User findUserByEmail(String email);

    List<RegisterDTO> findAllUsers();

    JwtResponse login(LoginDTO loginDto);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    MessageResponse resetPasswordByEmail(String email) throws AddressException;

    MessageResponse setNewPassword(ResetPasswordDTO resetPasswordDto);

    MessageResponse changePassword(ChangePasswordDTO changePasswordDTO);

    ResponseEntity<SuccessEditProfileDTO> editProfile(EditProfileDTO editProfileDTO);
}
