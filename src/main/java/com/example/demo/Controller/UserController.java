package com.example.demo.Controller;

import com.example.demo.Dto.AuthenticationRequestDto;
import com.example.demo.Dto.AuthenticationResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Dto.RequestVIPDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    UserRepository userRepository;

    @GetMapping("/verification")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("User is already verified.");
        }

        user.setVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok("User verified successfully!");
    }

    @PostMapping("/register/vip")
    public ResponseEntity<AuthenticationResponseDto> registerVIP(@RequestBody RequestVIPDto request) throws MessagingException {
        User user = userService.registerVIP(request);
        if(user == null){
            var error = AuthenticationResponseDto.builder()
                    .error("Credit Card is not valid!")
                    .build();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(userService.createToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) throws MessagingException {
        User user = userService.register(request);
        return ResponseEntity.ok(userService.createToken(user));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        User user = userService.login(request);
        return ResponseEntity.ok(userService.createToken(user));
    }

    @PostMapping("/password/change")
    public String passwordChange(@RequestBody RegisterRequestDto request) throws MessagingException {
        userService.passwordChange(request, 6);
        return "Password changed. Check your email!";
    }
}
