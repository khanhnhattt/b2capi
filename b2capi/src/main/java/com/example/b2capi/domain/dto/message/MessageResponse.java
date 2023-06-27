package com.example.b2capi.domain.dto.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse<T> {
    private T name;
}
