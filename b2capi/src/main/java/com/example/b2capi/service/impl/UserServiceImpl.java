package com.example.b2capi.service.impl;

import com.example.b2capi.domain.dto.LoginDTO;
import com.example.b2capi.domain.dto.RegisterDTO;
import com.example.b2capi.domain.model.Role;
import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.RoleRepository;
import com.example.b2capi.repository.UserRepository;
import com.example.b2capi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public void addUser(RegisterDTO registerDto) {
        User user = new User();

        user.setName(registerDto.getFirstName() + " " + registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());

        // Encrypt password using Spring Security
        user.setPassword(encoder.encode(registerDto.getPassword()));

        // Set role
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null)
        {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
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
    public void login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailOrUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


}
