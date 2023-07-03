package com.example.b2capi.domain.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessEditProfileDTO {
    private String name;
    @Email
    private String email;
    private String address;
    private String tel;
    private String username;
}
