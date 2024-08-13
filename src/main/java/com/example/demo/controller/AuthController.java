package com.example.demo.controller;

import com.example.demo.Service.impl.AuthenticationServiceImpl;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest signUpRequest) {
        return service.signup(signUpRequest);
    }

}
