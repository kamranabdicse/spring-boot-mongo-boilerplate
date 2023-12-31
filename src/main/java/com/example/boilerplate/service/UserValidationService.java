package com.example.boilerplate.service;

import com.example.boilerplate.repository.UserRepository;
import com.example.boilerplate.security.dto.RegistrationRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final String USERNAME_ALREADY_EXISTS = "username_already_exists";

    private final UserRepository userRepository;

    public void validateUser(RegistrationRequest registrationRequest){
        final String username = registrationRequest.getUsername();
        checkUsername(username);
    }

    private void checkUsername(String username) {
        final boolean existByUsername = userRepository.existsByUsername(username);
        if (existByUsername){
            log.warn("{} is already being used!", username);

        }
    }
}
