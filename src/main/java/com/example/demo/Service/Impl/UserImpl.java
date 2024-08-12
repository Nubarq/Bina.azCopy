package com.example.demo.Service.Impl;


import com.example.demo.Dto.AuthenticationRequestDto;
import com.example.demo.Dto.AuthenticationResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Dto.RequestVIPDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.CardRepository;
import com.example.demo.Repository.TokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserImpl implements UserService {

    UserRepository userRepository;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;
    JWTService jwtService;
    AuthenticationManager authenticationManager;
    CardRepository cardRepository;

    @Override
    public User register(RegisterRequestDto request) {
        var user = User.builder()
                .user_name(request.getUser_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User registerVIP(RequestVIPDto request) {
        var user = User.builder()
                .user_name(request.getUser_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.VIP)
                .build();
        var savedUser = userRepository.save(user);

        var creditCard = CreditCard.builder()
                .cardNumber(request.getCard_number())
                .expirationDate(request.getExpirationDate())
                .user(savedUser)
                .isActive(true)
                .build();
        if(creditCard.checkCardIsActive()){
            cardRepository.save(creditCard);

            return savedUser;
        }
        else{
            return null;
        }
    }

    @Override
    public User login(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        return user;
    }

    @Override
    public AuthenticationResponseDto createToken(User user){
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUser_id());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(1);
            t.setRevoked(1);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    @Override
    public void savedUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .token_type(TokenType.BEARER)
                .expired(0)
                .revoked(0)
                .build();
        tokenRepository.save(token);
    }
}
