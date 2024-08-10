package com.example.demo.Service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.model.User;

public interface AuthenticationService {
     User signup(SignUpRequest signUpRequest);
     JwtAuthenticationResponse signin(SigninRequest signinRequest);
     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)throws Exception;
}
