package com.example.demo.controller;

import com.example.demo.Service.CardService;
import com.example.demo.Service.JWTService;
import com.example.demo.dto.card.CardRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class CardController {
    private final CardService cardService;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    @PutMapping("/updateCard")
    public String updateCard(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CardRequest cardRequest) {

        return cardService.updateCard(authorizationHeader,cardRequest);
    }

    }
