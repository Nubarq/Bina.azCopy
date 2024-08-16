package com.example.demo.Service.Impl;

import com.example.demo.Dto.CardDto;
import com.example.demo.Entity.CreditCard;
import com.example.demo.Repository.CardRepository;
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

    @Override
    public CreditCard updateCard(CardDto request, int user_id) {
        CreditCard card = cardRepository.findByUser_UserId(user_id)
                .orElse(null);

        if(card != null) {
            System.out.println(request.getExpirationDate());
            card.setCardNumber(request.getCard_number());
            card.setExpirationDate(request.getExpirationDate());
            card.setActive(true);
            return cardRepository.save(card);
        }
        return null;
    }
}
