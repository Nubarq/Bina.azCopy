package com.example.demo.Service;


import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;

public interface CardService {

    CreditCard updateCard(CardDto request, int user_id);
}
