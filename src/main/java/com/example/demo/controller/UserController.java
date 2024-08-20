package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.dto.card.CardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/fromNormalToVIP/{userId}")
    public String fromNormalToVIP(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CardRequest cardRequest) {
        return userService.fromNormalToVIP(authorizationHeader,cardRequest);
    }

    @PutMapping("/fromVIPtoNormal/{userId}")
    public String fromVIPtoNormal(@RequestHeader("Authorization") String authorizationHeader) {
        return userService.fromVIPtoNormal(authorizationHeader);
    }
}
