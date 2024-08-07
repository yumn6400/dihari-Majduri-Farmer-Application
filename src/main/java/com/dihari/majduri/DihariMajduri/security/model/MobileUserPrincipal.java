package com.dihari.majduri.DihariMajduri.security.model;

import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MobileUserPrincipal implements UserDetails {
    private Farmer farmer ;
    public MobileUserPrincipal(Farmer farmer) {
        this.farmer =farmer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return farmer.getPin();
    }

    @Override
    public String getUsername() {
        return farmer.getMobileNumber();
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
