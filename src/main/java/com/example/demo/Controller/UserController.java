package com.example.demo.Controller;

import com.example.demo.Component.JwtAuthenticationFilter;
import com.example.demo.Dto.*;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping()
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    UserRepository userRepository;
    JWTService jwtService;


    @GetMapping("/api/auth/verification")
    public ResponseEntity<AuthenticationResponseDto> verifyUser(@RequestParam("token") String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        if (user.isVerified()) {
            var userError = AuthenticationResponseDto.builder()
                    .userError("user already verified")
                    .build();
            return ResponseEntity.badRequest().body(userError);
        }
        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok(userService.createToken(user));
    }

    @PostMapping("/api/auth/register/vip")
    public ResponseEntity<String> registerVIP(@RequestBody RequestVIPDto request) throws MessagingException {
        User user = userService.registerVIP(request);
        if(user == null){
            return ResponseEntity.badRequest().body("Credit Card is not valid!");
        }
        return ResponseEntity.ok("Please verify to continue. Verification link is sent to tour email. Your verification token is: " + user.getVerificationToken());
    }


    @PostMapping("/api/auth/register/guest")
    public ResponseEntity<String> registerGuest(@RequestBody RegisterRequestDto request) throws MessagingException {
        User user = userService.registerGuest(request);
        return ResponseEntity.ok("Please verify to continue. Verification link is sent to tour email. Your verification token is: " + user.getVerificationToken());

    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) throws MessagingException {
        User user = userService.register(request);
        return ResponseEntity.ok("Please verify to continue. Verification link is sent to tour email. Your verification token is: " + user.getVerificationToken());

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        User user = userService.login(request);
        return ResponseEntity.ok(userService.createToken(user));
    }

    @PostMapping("api/auth/password/forgot")
    public String passwordChange(@RequestBody AuthenticationRequestDto request) throws MessagingException {
        userService.forgotPassword(6, request);
        return "Password changed. Check your email!";
    }

    @PutMapping("/vip/touser")
    public ResponseEntity<String> VIPtoUser(@RequestHeader("Authorization") String token){
        token = token.replace("Bearer ", "");
        String email = jwtService.extractUserEmail(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow();
        String message = userService.VIPtoUser(user);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/user/tovip")
    public ResponseEntity<String> userToVIP(@RequestHeader("Authorization") String token, @RequestBody CardDto request){
        token = token.replace("Bearer ", "");
        String email = jwtService.extractUserEmail(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow();
        String message = userService.usertoVIP(user,request);
        return ResponseEntity.ok(message);
    }
}
