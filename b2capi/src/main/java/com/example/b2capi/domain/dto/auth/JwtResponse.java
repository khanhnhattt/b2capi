package com.example.b2capi.domain.dto.auth;

import com.example.b2capi.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
