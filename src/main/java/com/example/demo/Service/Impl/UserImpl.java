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
        String verificationText = "Thank you for visiting Bina.az, your trusted platform for property listings and real estate services.";
        String subject = "Welcome to Bina.az!";
        var user = User.builder()
                .user_name(request.getUser_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);

       sendEmail(savedUser, subject, verificationText);

        return savedUser;
    }

    @Override
    public User registerVIP(RequestVIPDto request) throws MessagingException {
        String verificationText = "Thank you for visiting Bina.az, your trusted platform for property listings and real estate services.";
        String subject = "Welcome to Bina.az!";
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
            sendEmail(savedUser, subject, verificationText);
            return savedUser;
        }
        else{
            return null;
        }
    }

    @Override
    public void passwordChange(RegisterRequestDto request, int n) throws MessagingException {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "0123456789"
        + "abcdefghijklmnopqrstuvxyz”";

        StringBuilder s = new StringBuilder(n);
        int y;
        for ( y = 0; y < n; y++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            s.append(AlphaNumericString
                    .charAt(index));
        }
        String newPassword = s.toString();

        String text = "Your new password is: " + newPassword;
        String subject = "Password Change";

        String encodedPassword = passwordEncoder.encode(s.toString());

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        sendEmail(savedUser, subject, text);
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
    public void sendEmail(User user, String subject, String text) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("BinaAz@mail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);

        mailSender.send(mimeMessage);
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
