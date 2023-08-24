package com.example.boilerplate.security.jwt;

import com.example.boilerplate.model.user.User;
import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import com.example.boilerplate.security.dto.LoginRequest;
import com.example.boilerplate.security.dto.LoginResponse;
import com.example.boilerplate.security.mapper.UserMapper;
import com.example.boilerplate.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

    private final UserService userService;
    private final CustomAuthenticationManager authenticationManager;

    private final JwtTokenManager jwtTokenManager;

    public LoginResponse getLoginResponse(LoginRequest loginRequest) {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();

        if (!authenticationManager.authenticate(username, password)){
            System.out.println("Not authenticated");
        }

        final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

        final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
        final String token = jwtTokenManager.generateToken(user);

        System.out.println("user has successfully logged in!");

        return new LoginResponse(token);
    }
}
