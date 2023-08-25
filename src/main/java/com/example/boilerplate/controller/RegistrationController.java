package com.example.boilerplate.controller;
import com.example.boilerplate.security.dto.RegistrationRequest;
import com.example.boilerplate.security.dto.RegistrationResponse;
import com.example.boilerplate.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest registrationRequest){
        log.info("start registration");
        RegistrationResponse registrationResponse = userService.registration(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }
}
