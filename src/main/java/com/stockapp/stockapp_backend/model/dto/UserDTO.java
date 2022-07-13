/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.stockapp.stockapp_backend.security.TokenUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String email;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String resetToken;
    private String phone;
    private String language;
    private LocalDateTime birthDate;

    private UserRole role;

    private Boolean activeUser;

    private LocalDateTime createdAt;

    private String warehouseName;
}
