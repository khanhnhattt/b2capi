package com.example.b2capi.service;

import com.example.b2capi.domain.model.User;
import com.example.b2capi.repository.UserRepository;
import com.sun.net.httpserver.HttpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {

    @Autowired
    private UserRepository userRepository;

    public String getUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public User getUser() {
        return userRepository.findByEmailOrUsername(getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
