package com.example.b2capi.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseMessage {
    private String code;
    private boolean success;
    private String msg;
    private String desc;
}
