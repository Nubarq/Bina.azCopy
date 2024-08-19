package com.example.demo.Controller;

import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CardService;
import com.example.demo.Service.JWTService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CardController {
    CardService cardService;
    UserRepository userRepository;
    JWTService jwtService;

    @PostMapping("/card/update")
    public ResponseEntity<CreditCard> updateCard(@RequestBody CardDto request, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String email = jwtService.extractUserEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(cardService.updateCard(request, user));
    }
}
