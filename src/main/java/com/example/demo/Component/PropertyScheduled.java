package com.example.demo.Component;

import com.example.demo.Entity.Property;
import com.example.demo.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropertyScheduled {

    private final PropertyRepository propertyRepository;

    @Scheduled(cron = "0 0 0 * * MON") // Run every week. This cron expression corresponds to once a week at midnight on Monday.
    public void removeExpiredPropertiesScheduled() {
        deactivateExpiredProperties();
    }

    // Run on application startup
    @EventListener(ContextRefreshedEvent.class)
    public void removeExpiredPropertiesOnStartup() {
        log.info("App start up");
        deactivateExpiredProperties();
    }

    public void deactivateExpiredProperties(){
        LocalDate today = LocalDate.now();
        try {
            List<Property> listProperties = propertyRepository.findByExpirationDateBefore(today);

            for (Property property : listProperties) {
                property.set_active(false);
            }

            log.info("Expired properties removed successfully.");
        } catch (Exception e) {
            log.error("Error occurred while removing expired properties: ", e);
        }
    }
}
