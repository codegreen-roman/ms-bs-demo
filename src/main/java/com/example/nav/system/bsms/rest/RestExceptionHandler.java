package com.example.nav.system.bsms.rest;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.StationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<StationErrorResponse> handleException(StationNotFoundException exception) {
        return new ResponseEntity<>(StationErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StationErrorResponse> fallback(Exception exception) {
        return new ResponseEntity<>(StationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
