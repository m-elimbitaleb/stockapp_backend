package com.stockapp.stockapp_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorDTO {
    String message;
    Integer status;
    String timestamp;
}
