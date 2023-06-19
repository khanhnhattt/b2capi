package com.example.b2capi.controller;

import com.example.b2capi.domain.dto.LoginDTO;
import com.example.b2capi.domain.dto.RegisterDTO;
import com.example.b2capi.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.b2capi.controller.base.BaseController;

@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {

    @Autowired
    IAuthService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerRequest) {

        // Check for duplicate username
        if (userService.existsByUsername(registerRequest.getUsername()))
            return new ResponseEntity<>("Username " + registerRequest.getUsername() + " is already taken", HttpStatus.BAD_REQUEST);
        // Check for duplicate email
        if (userService.existsByEmail(registerRequest.getEmail()))
            return new ResponseEntity<>("Email " + registerRequest.getEmail() + " is already taken", HttpStatus.BAD_REQUEST);
        // Check password matching
        if (!registerRequest.getPassword().equals(registerRequest.getMatchingPassword()))
            return new ResponseEntity<>("Password confirmation does not match", HttpStatus.BAD_REQUEST);

        // Add new User to DB
//        userService.addUser(registerRequest);
        return createSuccessResponse("Registered Successfully", userService.addUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        return createSuccessResponse("Logged In Successfully", userService.login(loginDto));
//        userService.login(loginDto);
//        return new ResponseEntity<>("Login to " + loginDto.getEmailOrUsername() + " successfully", HttpStatus.OK);
    }
}
