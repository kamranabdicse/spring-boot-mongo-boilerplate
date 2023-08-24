package com.example.boilerplate.security.jwt;
import com.example.boilerplate.model.user.User;
import com.example.boilerplate.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password){
        return isValidUserCredentials(username, password);
    }
    private boolean isValidUserCredentials(String username, String password) {
        User user = userService.findByUsername(username);

        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
