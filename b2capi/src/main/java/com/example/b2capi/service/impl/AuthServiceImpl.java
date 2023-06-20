package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.auth.JwtResponse;
import com.example.b2capi.domain.dto.auth.LoginDTO;
import com.example.b2capi.domain.dto.auth.RegisterDTO;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.Role;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.RoleRepository;
import com.example.b2capi.repository.UserRepository;
import com.example.b2capi.security.config.JwtUtils;
import com.example.b2capi.security.service.UserDetailsImpl;
import com.example.b2capi.service.IAuthService;
import com.example.b2capi.service.IResetPasswordTokenService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IResetPasswordTokenService IResetPasswordTokenService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JavaMailSenderImpl mailSender;
    private final HttpServletRequest request;


    @Override
    public MessageResponse addUser(RegisterDTO registerDto) {
        // Check for duplicate username
        if (this.existsByUsername(registerDto.getUsername()))
            throw new IllegalArgumentException("Username " + registerDto.getUsername() + " is already taken");
        // Check for duplicate email
        if (this.existsByEmail(registerDto.getEmail()))
            throw new IllegalArgumentException("Email " + registerDto.getEmail() + " is already taken");
        // Check password matching
        if (!registerDto.getPassword().equals(registerDto.getMatchingPassword()))
            throw new IllegalArgumentException("Password confirmation does not match");

        User user = new User();

        user.setName(registerDto.getFirstName() + " " + registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());

        // Encrypt password using Spring Security
        user.setPassword(encoder.encode(registerDto.getPassword()));

        // Set role
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
        return MessageResponse.builder().name("User Registered Successfully!").build();
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<RegisterDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<RegisterDTO> registerDtos = users
                .stream()
                .map(user -> mapToUserDTO(user))
                .toList();
        return registerDtos;
    }

    // Map function from User to UserDTO
    private RegisterDTO mapToUserDTO(User user) {
        RegisterDTO registerDto = new RegisterDTO();
        String[] name = user.getName().split(" ");
        registerDto.setFirstName(name[0]);
        registerDto.setLastName(name[1]);
        registerDto.setEmail(user.getEmail());
        return registerDto;
    }

    @Override
    public JwtResponse login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailOrUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .toList();

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Value("${context-path}")
    private String contextPath;
    @Override
    public MessageResponse resetPasswordByEmail(String email) throws AddressException {
        User user = this.findUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);

        // Generate token
        String token = UUID.randomUUID().toString();
        IResetPasswordTokenService.createPasswordResetTokenForUser(user, token, LocalDateTime.now().plusMinutes(5));    // expiration time = 5 minutes

        // Send token through email
        mailSender.send(constructResetTokenEmail(contextPath, request.getLocale(), token, user));

        return MessageResponse.builder().name("Email sent to "+ email).build();
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) throws AddressException {
        String url = contextPath + "/auth/change_password?token=" + token;
//        String message = getMessage("message.resetPassword",
//                null, locale);
        String message = "Link to reset password";
        return constructEmail("Reset Password Link", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) throws AddressException {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(String.valueOf(new InternetAddress("khanhhn.hoang@gmail.com")));
        return email;
    }

}
