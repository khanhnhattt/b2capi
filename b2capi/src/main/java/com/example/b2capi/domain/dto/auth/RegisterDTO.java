package com.example.b2capi.domain.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    private String firstName;
    private String lastName;
    @Email
    private String email;
//    private String address;
//    private String tel;
//    private int userTypeId;
    private String username;
    private String password;
    private String matchingPassword;

}
