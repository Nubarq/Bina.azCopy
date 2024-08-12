package com.example.demo.Service;

import com.example.demo.Dto.AuthenticationRequestDto;
import com.example.demo.Dto.AuthenticationResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Dto.RequestVIPDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.User;

public interface UserService {

    User register(RegisterRequestDto request);

    User registerVIP(RequestVIPDto request);

    User login(AuthenticationRequestDto request);

    AuthenticationResponseDto createToken(User user);

    void revokeAllUserTokens(User user);

    void savedUserToken(User user, String jwtToken);
}
