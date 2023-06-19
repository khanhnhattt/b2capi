package com.example.b2capi.domain.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String emailOrUsername;
    private String password;
}
