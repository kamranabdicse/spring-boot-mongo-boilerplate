package com.example.boilerplate.security.service;

import com.example.boilerplate.model.user.UserRole;
import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private static final String USERNAME_OR_PASSWORD_INVALID = "Invalid username or password";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

        if(Objects.isNull(authenticatedUserDto)){
            System.out.println(USERNAME_OR_PASSWORD_INVALID);
        }

        final String authenticatedUsername = authenticatedUserDto.getUsername();
        final String authenticatedPassword = authenticatedUserDto.getPassword();
        final UserRole userRole = authenticatedUserDto.getUserRole();
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.toString());
        return new User(authenticatedUsername, authenticatedPassword, Collections.singletonList(grantedAuthority));
    }
}
