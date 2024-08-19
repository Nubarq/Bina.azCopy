package com.example.demo.Component;

import com.example.demo.Entity.CreditCard;
import com.example.demo.Entity.Property;
import com.example.demo.Repository.CardRepository;
import com.example.demo.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyScheduled {

    private final PropertyRepository propertyRepository;
    private final CardRepository cardRepository;

    @Scheduled(cron = "0 0 0 * * *")//@Scheduled(cron = "0 0 0 * * *")
    public void deactivateExpiredProperties() {
        LocalDate today = LocalDate.now();

        try {
            List<Property> listProperties = propertyRepository.findByExpirationDateBefore(today);

            if (listProperties != null && !listProperties.isEmpty()) {
                for (Property property : listProperties) {
                    property.set_active(false);
                    log.info("Expired properties removed successfully with ID: {}", property.getPropertyId());
                    propertyRepository.save(property);
                }
            }

            List<CreditCard> listCreditCards = cardRepository.findByExpirationDateBefore(today);

            if (listCreditCards != null && !listCreditCards.isEmpty()) {
                for (CreditCard creditCard : listCreditCards) {
                    creditCard.setActive(false);
                    cardRepository.save(creditCard);
                }
            }

        } catch (Exception e) {
            log.error("Error occurred while removing expired properties: ", e);
        }
    }
}
