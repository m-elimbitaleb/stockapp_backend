package com.stockapp.stockapp_backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class BusinessExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<ErrorDTO> handleConflict(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now().toString()).build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorDTO> handleConflict(Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorDTO error = ErrorDTO.builder().message("Unknown error occurred, please contact your administrator")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now().toString()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
