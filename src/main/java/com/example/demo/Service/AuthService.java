package com.example.demo.Service;

import com.example.demo.dto.login.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String loginUser(LoginRequest loginRequest, HttpServletResponse response);
    void logoutUser(HttpServletRequest request, HttpServletResponse response);
}
