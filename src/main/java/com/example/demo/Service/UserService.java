package com.example.demo.Service;

import com.example.demo.Dto.AuthenticationRequestDto;
import com.example.demo.Dto.AuthenticationResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Dto.RequestVIPDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.User;
import jakarta.mail.MessagingException;

public interface UserService {

    User register(RegisterRequestDto request) throws MessagingException;

    User registerVIP(RequestVIPDto request) throws MessagingException;

    User login(AuthenticationRequestDto request);

    AuthenticationResponseDto createToken(User user);

    void revokeAllUserTokens(User user);

    void savedUserToken(User user, String jwtToken);

    void sendEmail(User user, String subject, String text) throws MessagingException;

    void passwordChange(RegisterRequestDto request, int n) throws MessagingException;
}
