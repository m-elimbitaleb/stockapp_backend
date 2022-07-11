package com.stockapp.stockapp_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorDTO {
    String message;
    Integer status;
    String timestamp;
}
