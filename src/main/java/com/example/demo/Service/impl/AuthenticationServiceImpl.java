package com.example.demo.Service.impl;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.JWTService;
import com.example.demo.dto.*;
import com.example.demo.model.Card;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
//import com.mailersend.sdk.Recipient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import com.mailersend.sdk.Email;
//import com.mailersend.sdk.MailerSend;
//import com.mailersend.sdk.MailerSendResponse;
//import com.mailersend.sdk.exceptions.MailerSendException;
//import org.springframework.mail.javamail.JavaMailSender;
import java.util.HashMap;

@Service
//@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final JavaMailSender mailSender;
    @Autowired
    private final AuthenticationManager authenticationManager;
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, JavaMailSender mailSender, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailSender=mailSender;
        this.authenticationManager = authenticationManager;
    }




    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("BinaCopy@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        this.mailSender.send(simpleMailMessage);

    }




    @Override
    public JwtAuthenticationResponse signup(SignUpRequest signUpRequest) {
        User user = new User();
        System.out.println("The password is: => "+signUpRequest.getPassword());

        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));



        sendEmail(signUpRequest.getEmail(),"welcome","welcome to Bina.az");
        User savedUser= userRepository.save(user);
        var jwt = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;

    }



    @Override
    public JwtAuthenticationResponse signupVIP(SignUpVIP signUpVIP) {
        User user = new User();
        Card card = new Card();
        card.setCardNumber(signUpVIP.getCardNumber());
        card.setExpirationDate(signUpVIP.getExpirationDate());
        card.checkCardIsActive();


        user.setCard(card);
        user.setEmail(signUpVIP.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpVIP.getPassword()));
        User savedUser= userRepository.save(user);
        var jwt = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


    @Override
    public JwtAuthenticationResponse signin(SigninRequest signinRequest)throws IllegalArgumentException {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail());
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)throws Exception{
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail);
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
