package com.example.demo.Service;

import com.example.demo.Dto.*;
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

    void forgotPassword(int n, AuthenticationRequestDto request) throws MessagingException;

    String VIPtoUser(User user);

    String usertoVIP(User user, CardDto request);
}
