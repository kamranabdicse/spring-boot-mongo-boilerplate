package com.example.boilerplate.security.jwt;

import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class AuthenticationImpl implements Authentication {
    private final AuthenticatedUserDto authenticatedUserDto;
    private boolean isAuthenticated = true;

    public AuthenticationImpl(AuthenticatedUserDto authenticatedUserDto) {
        this.authenticatedUserDto = authenticatedUserDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUserDto.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return authenticatedUserDto.getUsername();
    }
}
