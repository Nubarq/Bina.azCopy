package com.example.demo.Service.impl;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.JWTService;
import com.example.demo.dto.*;
import com.example.demo.model.Card;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
//import com.mailersend.sdk.Recipient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final JavaMailSender mailSender;
    @Autowired
    private final AuthenticationManager authenticationManager;
    public AuthenticationServiceImpl(CardRepository cardRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, JavaMailSender mailSender, AuthenticationManager authenticationManager){
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailSender=mailSender;
        this.authenticationManager = authenticationManager;
    }
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();
    private String generatedPassword;


    public void sendEmail(String to, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("BinaCopy@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message, true);

        mailSender.send(mimeMessage);

    }


    @Override
    public JwtAuthenticationResponse signup(SignUpRequest signUpRequest) throws MessagingException {
        String token = UUID.randomUUID().toString();

        User user = new User();
        System.out.println("The password is: => "+signUpRequest.getPassword());

        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setUserType(UserType.NORMAL);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setVerificationToken(token);
        User savedUser= userRepository.save(user);

        String link = "http://localhost:8080/api/v1/auth/verification?token=" + token;
        String verificationText = "<html>"
                + "<body>"
                + "<p>Thank you for visiting <strong>Bina.az</strong>, your trusted platform for property listings and real estate services.</p>"
                + "<p>Please click the link below to verify your account:</p>"
                + "<p><a href=\"" + link + "\">Verify your account</a></p>"
                + "</body>"
                + "</html>";

        String subject = "Welcome to Bina.az!";

        sendEmail(savedUser.getEmail(), subject, verificationText);



        var jwt = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);
        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;

    }


    @Override
    public JwtAuthenticationResponse signupVIP(SignUpVIP signUpVIP) throws MessagingException {
        String token = UUID.randomUUID().toString();
        System.out.println("Token: "+token);

        User user = new User();
        user.setEmail(signUpVIP.getEmail());
        user.setVerificationToken(token);
        user.setRole(Role.USER);
        user.setUserType(UserType.VIP);
        user.setPassword(passwordEncoder.encode(signUpVIP.getPassword()));
        User savedUser= userRepository.save(user);

        Card card = new Card();
        card.setCardNumber(signUpVIP.getCardNumber());
        card.setExpirationDate(signUpVIP.getExpirationDate());
        card.setUser(savedUser);
        if (card.checkCardIsActive()){
             cardRepository.save(card);

            String link = "http://localhost:8080/api/v1/auth/verification?token=" + token;
            String verificationText = "<html>"
                    + "<body>"
                    + "<p>Thank you for visiting <strong>Bina.az</strong>, your trusted platform for property listings and real estate services.</p>"
                    + "<p>Please click the link below to verify your account:</p>"
                    + "<p><a href=\"" + link + "\">Verify your account</a></p>"
                    + "</body>"
                    + "</html>";
            String subject = "Welcome to Bina.az!";

            sendEmail(savedUser.getEmail(), subject, verificationText);

            var jwt = jwtService.generateToken(savedUser);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;
        }
        System.out.println("card is NOT ACTIVE");
        return null;
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

    @Override
    public void forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);

        int length = MIN_LENGTH + RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC.length());
            password.append(ALPHANUMERIC.charAt(index));
        }
        this.generatedPassword = password.toString();

        // Encode the password before saving it
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(this.generatedPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Send email with the new password
        sendEmail(email, "Password Reset", "Your new password is: " + this.generatedPassword);

    }
    public String getGeneratedPassword() {
        return generatedPassword;
    }

}
