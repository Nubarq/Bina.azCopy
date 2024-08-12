package com.example.demo.Controller;

import com.example.demo.Dto.AuthenticationRequestDto;
import com.example.demo.Dto.AuthenticationResponseDto;
import com.example.demo.Dto.RegisterRequestDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class UserController {

    UserService userService;



    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) {
        User user = userService.register(request);
        return ResponseEntity.ok(userService.createToken(user));

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        User user = userService.login(request);
        return ResponseEntity.ok(userService.createToken(user));
    }
}
