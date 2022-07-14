/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.security;

import com.stockapp.stockapp_backend.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser extends UserDTO {

    private boolean admin;
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
