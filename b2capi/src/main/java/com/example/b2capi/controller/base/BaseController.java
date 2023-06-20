package com.example.b2capi.controller.base;

import com.example.b2capi.domain.dto.message.BaseMessage;
import com.example.b2capi.domain.dto.message.ExtendedMessage;
import org.apache.commons.lang3.text.ExtendedMessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    /**
     * Input: data from Controllers
     * Output: ResponseEntity
     * @param msg
     * @param desc
     * @param data
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<?> createSuccessResponse(String msg, String desc, T data)
    {
        ExtendedMessage<T> responseMessage = new ExtendedMessage<>(String.valueOf(HttpStatus.OK.value()), true, msg, desc, data);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    public <T> ResponseEntity<?> createSuccessResponse(String msg, T data)
    {
        return createSuccessResponse(msg, null, data);
    }
    public <T> ResponseEntity<?> createSuccessResponse(T data)
    {
        return createSuccessResponse(null, null, data);
    }
    public <T> ResponseEntity<?> createSuccessResponse()
    {
        return createSuccessResponse(null, null, null);
    }

    /**
     * Handles failure responses from Controllers
     * @param code
     * @param msg
     * @param desc
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<?> createFailureResponse(String code, String msg, String desc)
    {
        BaseMessage baseMessage = new BaseMessage(code,false, msg, desc);
        return new ResponseEntity<>(baseMessage, HttpStatus.valueOf(Integer.valueOf(code)));
    }
}
