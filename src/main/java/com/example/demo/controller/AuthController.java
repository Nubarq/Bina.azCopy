package com.example.demo.controller;

import com.example.demo.Exeption.CustomException;
import com.example.demo.Service.impl.AuthenticationServiceImpl;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SignUpVIP;
import com.example.demo.dto.SigninRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationServiceImpl service;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
        return service.signup(signUpRequest);
    }

    @PostMapping("/registerVIP")
    public JwtAuthenticationResponse signupVIP(@RequestBody SignUpVIP signUpVIP) throws MessagingException {
        return service.signupVIP(signUpVIP);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SigninRequest signinRequest) {
        return service.signin(signinRequest);
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(String email) throws MessagingException {
        service.forgotPassword(email);
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new CustomException("Invalid verification token"));

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("User is already verified.");
        }

        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok("User verified successfully!");
    }


    }
