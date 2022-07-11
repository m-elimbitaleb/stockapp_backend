/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser {
    private Long id;
    private boolean admin;

    private String firstName;
    private String phone;
    private String lastName;
    private LocalDateTime birthDate;
    private String email;
    private String username;
    private String password;
    private String lang;
    private boolean rememberMe;
    private String[] permissions;


    /*
     * Set user permissions from a collection of GrantedAuthorities
     * */
    public void setPermissionsFromGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.permissions = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

}
