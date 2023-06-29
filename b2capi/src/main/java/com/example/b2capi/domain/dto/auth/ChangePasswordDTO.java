package com.example.b2capi.domain.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String matchingPassword;
}
