package com.example.demo.Service;

import com.example.demo.dto.card.CardRequest;
import com.example.demo.dto.user.response.CreateUserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    CreateUserResponseDto findUserById(Integer id);
    List<CreateUserResponseDto> findAllUsers();

    public UserDetailsService userDetailsService();

    public String fromNormalToVIP(String authorizationHeader, CardRequest cardRequest);
    public String fromVIPtoNormal(String authorizationHeader);
}
