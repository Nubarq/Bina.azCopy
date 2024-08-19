package com.example.demo.Service.Impl;

import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CardRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CardImpl implements CardService {

    CardRepository cardRepository;
    UserRepository userRepository;

    @Override
    public CreditCard updateCard(CardDto request, User user) {
        CreditCard card = user.getCard();

        if(card != null) {
            card.setCardNumber(request.getCard_number());
            card.setExpirationDate(request.getExpirationDate());
            card.setActive(true);
            return cardRepository.save(card);
        }
        return null;
    }


}
