/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model;

import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.stockapp.stockapp_backend.security.TokenUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ToString.Include
    private String email;

    @ToString.Include
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String resetToken;
    @ToString.Include
    private String phone;
    private String language;
    private LocalDateTime birthDate;

    @Enumerated(EnumType.ORDINAL)
    @ToString.Include
    private UserRole role;

    private Boolean activeUser;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public TokenUser toTokenUser(String warehouseName) {
        TokenUser tokenUser = new TokenUser();
        tokenUser.setId(getId());
        tokenUser.setUsername(getUsername());
        tokenUser.setFirstName(getFirstName());
        tokenUser.setLastName(getLastName());
        tokenUser.setEmail(getEmail());
        tokenUser.setBirthDate(getBirthDate());
        tokenUser.setPhone(getPhone());
        tokenUser.setPassword(null);
        tokenUser.setLanguage(getLanguage());
        tokenUser.setRememberMe(false);
        tokenUser.setCreatedAt(getCreatedAt());
        tokenUser.setWarehouseName(getWarehouse().getName());
        tokenUser.setAdmin(UserRole.ADMIN.equals(this.role));
        tokenUser.setPermissions(new String[]{this.role.toString()});
        tokenUser.setWarehouseName(warehouseName);
        return tokenUser;
    }

    @PrePersist
    public void prePersist() {
        if (this.getId() == null) this.createdAt = LocalDateTime.now();
    }
}
