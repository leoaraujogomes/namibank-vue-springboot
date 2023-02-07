package com.project.transffinanceiraspringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestException(RequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Exception exception = new Exception(
                e.getMessage(),
                e,
                badRequest
        );
        return new ResponseEntity<>(exception, badRequest);
    }

}
