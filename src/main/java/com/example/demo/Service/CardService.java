package com.example.demo.Service;

import com.example.demo.dto.card.CardRequest;

public interface CardService {
    String updateCard(String authorizationHeader,CardRequest cardRequest);
}
