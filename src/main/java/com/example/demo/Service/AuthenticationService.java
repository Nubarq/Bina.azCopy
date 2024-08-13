package com.example.demo.Service;

import com.example.demo.dto.*;
import com.example.demo.model.User;

public interface AuthenticationService {
     JwtAuthenticationResponse signup(SignUpRequest signUpRequest);
     JwtAuthenticationResponse signupVIP(SignUpVIP signUpVIP);

     JwtAuthenticationResponse signin(SigninRequest signinRequest);
     JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)throws Exception;

     void forgotPassword(String email);
}
