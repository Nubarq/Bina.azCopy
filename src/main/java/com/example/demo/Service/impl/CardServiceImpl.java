package com.example.demo.Service.impl;

import com.example.demo.Exeption.CustomException;
import com.example.demo.Service.CardService;
import com.example.demo.Service.JWTService;
import com.example.demo.dto.card.CardRequest;
import com.example.demo.dto.card.CardResponse;
import com.example.demo.mapper.CardMapper;
import com.example.demo.model.Card;
import com.example.demo.model.User;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;
    private final JWTService jwtService;
    @Override
    public String updateCard(String authorizationHeader,CardRequest cardRequest) {

        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract the username from the token
        String username = jwtService.extractUsername(token);

        // Fetch the user from the database
        User user = userRepository.findByEmail(username);

//        User user=userRepository.findById(userId).orElseThrow(() -> new CustomException("not found "+userId+" property"));
        Card card = user.getCard();
        card.setCardNumber(cardRequest.getCardNumber());
        card.setExpirationDate(cardRequest.getExpirationDate());
        card.setActive(true);
        card.setUser(user);
        cardRepository.save(card);
        CardResponse response = cardMapper.mapCardEntityToCardResponseDTO(card);
        user.setCard(card);

        return "Your card updated successfuly. New card: "+response;
    }
}
