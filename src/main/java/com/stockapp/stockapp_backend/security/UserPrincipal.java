/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.security;

import com.stockapp.stockapp_backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = -2308975410465789235L;
    private final User user;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserPrincipal(User user) {
        this.user = user;
        if (user.getRole() != null) {
            this.authorities = AuthorityUtils.createAuthorityList(user.getRole().name());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (user == null) {
            return Collections.emptyList();
        }
        // Extract list of permissions (name)

        return authorities;
    }

    @Override
    public String getPassword() {
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActiveUser();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getActiveUser();
    }

    public TokenUser toTokenUser(String warehouseName) {
        TokenUser tokenUser = user.toTokenUser(warehouseName);
        tokenUser.setPermissionsFromGrantedAuthorities(this.getAuthorities());
        return tokenUser;
    }
}
