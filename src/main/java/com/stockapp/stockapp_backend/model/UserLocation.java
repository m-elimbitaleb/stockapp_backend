package com.stockapp.stockapp_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLocation {

    private String label;

    private Double latitude;
    private Double longitude;

    private LocalDateTime lastUpdated;
}
