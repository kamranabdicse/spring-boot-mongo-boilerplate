package com.example.boilerplate.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticatedUserDto {

    String firstName;

    String lastName;

    String username;

    String password;
}
