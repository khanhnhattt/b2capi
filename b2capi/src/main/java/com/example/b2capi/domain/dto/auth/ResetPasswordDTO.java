package com.example.b2capi.domain.dto.auth;

import com.example.b2capi.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class ResetPasswordDTO {
    private String token;
    private String password;
    private String matchingPassword;
}
