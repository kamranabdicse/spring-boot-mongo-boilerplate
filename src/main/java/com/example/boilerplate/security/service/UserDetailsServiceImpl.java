package com.example.boilerplate.security.service;
import com.example.boilerplate.exceptions.UserNotFoundException;
import com.example.boilerplate.security.dto.AuthenticatedUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserService userService;

    private static final String USERNAME_OR_PASSWORD_INVALID = "Invalid username or password";

    public AuthenticatedUserDto loadUserByUsername(String username) throws UsernameNotFoundException {

        final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

        if(Objects.isNull(authenticatedUserDto)){
            throw new UserNotFoundException("username not found");
        }
        return authenticatedUserDto;
    }
}
