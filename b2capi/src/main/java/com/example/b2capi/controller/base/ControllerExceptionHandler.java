package com.example.b2capi.controller.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler extends BaseController {
    /**
     * Handles exceptions from Controllers
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e)
    {
        return createFailureResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                Objects.requireNonNull(e.getFieldError().getDefaultMessage()),
                null);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(IllegalArgumentException e)
    {
        return createFailureResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                e.getMessage(),
                null);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(Exception e)
    {
        return createFailureResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e.getMessage(),
                null);
    }
}
