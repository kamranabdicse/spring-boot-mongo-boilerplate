package com.example.boilerplate.security.service;

import com.example.boilerplate.model.user.User;
import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import com.example.boilerplate.security.dto.RegistrationRequest;
import com.example.boilerplate.security.dto.RegistrationResponse;

public interface UserService {

    User findByUsername(String username);
    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
