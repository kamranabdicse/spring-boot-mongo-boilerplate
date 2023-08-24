package com.example.boilerplate.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

}
