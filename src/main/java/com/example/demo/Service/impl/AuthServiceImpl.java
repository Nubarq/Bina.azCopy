package com.example.demo.Service.impl;

import com.example.demo.Service.AuthService;
import com.example.demo.dto.login.request.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public String loginUser(LoginRequest loginRequest, HttpServletResponse response) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        boolean rememberMe = loginRequest.isRememberMe();

        log.debug("Login request received: email={}, rememberMe={}", email, rememberMe);


        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            log.debug("Invalid email or password for email: {}", email);
            //throw new CustomException("This is a custom exception");

            return "Invalid email or password";
        }

        if (rememberMe) {
            Cookie cookie = new Cookie("rememberMe", email);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "Login successful";
    }

    @Override
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("rememberMe", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

    }
}
