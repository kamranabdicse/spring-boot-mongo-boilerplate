package com.example.boilerplate.security.dto;

import com.example.boilerplate.model.user.User;
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
