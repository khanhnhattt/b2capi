package com.example.b2capi.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedMessage<T> extends BaseMessage {
    private T data;

    public ExtendedMessage(String code, boolean success, String msg, String desc, T data) {
        super(code, success, msg, desc);
        this.data = data;
    }
}
