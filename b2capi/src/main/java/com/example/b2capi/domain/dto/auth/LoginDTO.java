package com.example.b2capi.domain.dto.auth;

import lombok.Data;

@Data
public class LoginDTO {
    private String emailOrUsername;
    private String password;
}
