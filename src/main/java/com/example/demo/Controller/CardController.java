package com.example.demo.Controller;

import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Service.CardService;
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

    @PostMapping("{user_id}/card/update")
    public ResponseEntity<CreditCard> updateCard(@RequestBody CardDto request, @PathVariable int user_id) {
        return ResponseEntity.ok(cardService.updateCard(request, user_id));
    }
}
