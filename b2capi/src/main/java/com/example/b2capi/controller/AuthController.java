package com.example.b2capi.controller;

import com.example.b2capi.domain.dto.auth.*;
import com.example.b2capi.service.IAuthService;
import com.example.b2capi.service.IResetPasswordTokenService;
import jakarta.mail.internet.AddressException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.b2capi.controller.base.BaseController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController {

    @Autowired
    IAuthService authService;

    @Autowired
    IResetPasswordTokenService resetPasswordTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerRequest) {
        return createSuccessResponse("Registered Successfully", authService.addUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        return createSuccessResponse("Logged In Successfully", authService.login(loginDto));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") @Valid @Email String email) throws AddressException {
        return createSuccessResponse("Email sent to " + email, authService.resetPasswordByEmail(email));
    }

    @GetMapping("/forgot_password")
    public ResponseEntity<?>  showChangePasswordPage(@RequestParam("token") String token) {
        return createSuccessResponse("Reset Token Valid", resetPasswordTokenService.validateToken(token));      // Stackoverflow recursion
    }

    @PutMapping("/forgot_password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDto)
    {
        return createSuccessResponse("Reset Password Successfully", authService.setNewPassword(resetPasswordDto));
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO)
    {
        return createSuccessResponse("Password changed successfully", authService.changePassword(changePasswordDTO));
    }

    @PutMapping("/edit_profile")
    public ResponseEntity<SuccessEditProfileDTO> editProfile(@RequestBody EditProfileDTO editProfileDTO)
    {
        return authService.editProfile(editProfileDTO);
    }

}
