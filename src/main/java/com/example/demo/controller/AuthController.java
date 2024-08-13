package com.example.demo.controller;

import com.example.demo.Service.impl.AuthenticationServiceImpl;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SignUpVIP;
import com.example.demo.dto.SigninRequest;
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

    @PostMapping("/registerVIP")
    public JwtAuthenticationResponse signupVIP(@RequestBody SignUpVIP signUpVIP) {
        return service.signupVIP(signUpVIP);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SigninRequest signinRequest) {
        return service.signin(signinRequest);
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(String email) {
        service.forgotPassword(email);
    }


    }
