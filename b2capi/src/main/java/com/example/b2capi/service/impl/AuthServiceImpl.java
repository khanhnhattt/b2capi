package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.auth.*;
import com.example.b2capi.domain.dto.message.MessageResponse;
import com.example.b2capi.domain.model.Role;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.ResetPasswordTokenRepository;
import com.example.b2capi.repository.RoleRepository;
import com.example.b2capi.repository.UserRepository;
import com.example.b2capi.security.config.JwtUtils;
import com.example.b2capi.security.service.UserDetailsImpl;
import com.example.b2capi.service.BaseService;
import com.example.b2capi.service.IAuthService;
import com.example.b2capi.service.IResetPasswordTokenService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl extends BaseService implements IAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final IResetPasswordTokenService IResetPasswordTokenService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JavaMailSenderImpl mailSender;
    private final HttpServletRequest request;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RegisterSuccessDTO addUser(RegisterDTO registerDto) {
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
        user.setAddress(registerDto.getAddress());
        user.setTel(registerDto.getTel());

        // Encrypt password using Spring Security
        user.setPassword(encoder.encode(registerDto.getPassword()));

        // Set role
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));

        userRepository.save(user);

        return modelMapper.map(user, RegisterSuccessDTO.class);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
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

        return MessageResponse.builder().name("Email sent to " + email).build();
    }

    @Override
    public MessageResponse setNewPassword(ResetPasswordDTO resetPasswordDto) {
        // Check matching password
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getMatchingPassword()))
            throw new IllegalArgumentException("Password Confirmation Not Matching");

        // Check token available
        String result = IResetPasswordTokenService.isTokenAvailable(resetPasswordDto.getToken());

        if (result != null) throw new IllegalArgumentException(result);     // Throw exception if token invalid

        User user = resetPasswordTokenRepository.findByToken(resetPasswordDto.getToken()).getUser();
        this.updatePassword(user, resetPasswordDto.getPassword());
        return MessageResponse.builder().build();
    }

    @Override
    public MessageResponse changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = getUser();

        // Check old password
        if (!encoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old Password invalid");
        }

        // Check matching password
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getMatchingPassword())) {
            throw new IllegalArgumentException("Password Confirmation not matched");
        }

        // Update
        updatePassword(user, changePasswordDTO.getNewPassword());
        return MessageResponse.builder().name("Password changed successfully").build();
    }

    @Override
    public ResponseEntity<SuccessEditProfileDTO> editProfile(EditProfileDTO editProfileDTO) {
        User user = getUser();
        user.setAddress(editProfileDTO.getAddress());
        user.setName(editProfileDTO.getName());
        user.setTel(editProfileDTO.getTel());

        // Check valid username
        if (!user.getUsername().equals(editProfileDTO.getUsername()) && !this.existsByUsername(editProfileDTO.getUsername()))
        {
            user.setUsername(editProfileDTO.getUsername());
        }
        else throw new IllegalArgumentException("Username " + editProfileDTO.getUsername() + " is already taken");

        // Check valid email
        if (!user.getEmail().equals(editProfileDTO.getEmail()) && !this.existsByEmail(editProfileDTO.getEmail()))
        {
            user.setEmail(editProfileDTO.getEmail());
        }
        else throw new IllegalArgumentException("Email " + editProfileDTO.getEmail() + " is already taken");


        userRepository.save(user);

        SuccessEditProfileDTO successEditProfileDTO = modelMapper.map(user, SuccessEditProfileDTO.class);
        return ResponseEntity.ok().body(successEditProfileDTO);
    }

    private void updatePassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) throws AddressException {
        String url = contextPath + "/auth/change_password?token=" + token;
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
