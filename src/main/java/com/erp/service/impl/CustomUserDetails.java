package com.erp.service.impl;

import com.erp.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UUID uuid;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity userEntity) {
        this.uuid = userEntity.getUuid();
        this.username = userEntity.getName();
        this.password = userEntity.getPassword();
        this.authorities = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
