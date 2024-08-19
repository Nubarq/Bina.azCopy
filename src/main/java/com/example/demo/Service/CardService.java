package com.example.demo.Service;


import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.User;

public interface CardService {

    CreditCard updateCard(CardDto request, User user);
}
