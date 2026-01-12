package com.example.demo.Service;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
     JwtAuthenticationResponse signup(SignUpRequest signUpRequest) throws MessagingException;
     JwtAuthenticationResponse signupVIP(SignUpVIP signUpVIP) throws MessagingException;

     JwtAuthenticationResponse signin(SigninRequest signinRequest);
     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)throws Exception;

     void forgotPassword(String email) throws MessagingException;
}
