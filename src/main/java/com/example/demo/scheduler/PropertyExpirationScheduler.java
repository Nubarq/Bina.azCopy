package com.example.demo.scheduler;


import com.example.demo.model.Card;
import com.example.demo.model.Property;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class PropertyExpirationScheduler {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private CardRepository cardRepository;


    @Scheduled(cron = "0 0 0 * * ?") // This cron expression runs the method at midnight every day
    public void deactivateExpiredProperties() {
        List<Property> expiredProperties = propertyRepository.findByIsActiveTrueAndExpirationDateAfter(new Date());

        for (Property property : expiredProperties) {
            property.setActive(false);
            propertyRepository.save(property);
        }
    }

    @Scheduled(cron = "0 * * * * ?") // This cron expression runs the method at midnight every day
    public void deactivateExpiredCards() {
        // Fetch all active cards with an expiration date before today
        List<Card> expiredCards = cardRepository.findByIsActiveTrueAndExpirationDateBefore(new Date());

        for (Card card : expiredCards) {
            // Only set the card to inactive if it is currently active
            if (card.isActive()) {
                card.setActive(false);
                cardRepository.save(card);
            }
        }
    }

}
