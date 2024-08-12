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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserImpl implements UserService {

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final PasswordEncoder passwordEncoder;
    final JWTService jwtService;
    final AuthenticationManager authenticationManager;
    final CardRepository cardRepository;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public User register(RegisterRequestDto request) throws MessagingException {
        var user = User.builder()
                .user_name(request.getUser_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("BinaAz@mail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Welcome");
        mimeMessageHelper.setText("Welcome to Bina.az!");

        mailSender.send(mimeMessage);

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
