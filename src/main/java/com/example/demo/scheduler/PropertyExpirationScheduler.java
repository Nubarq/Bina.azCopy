package com.example.demo.scheduler;

import com.alas.lemlist.model.Video;
import com.alas.lemlist.repository.VideoRepository;
import com.example.demo.model.Property;
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

    private static final String folderLocation = "C:/lemlistVideo";

//    @Scheduled(cron = "0 0 0 * * ?") // This will run once a day at midnight
//    public void cleanUpOldVideos() throws InterruptedException {
//        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(7);
//        List<Video> oldVideos = videoRepository.findByCreatedAtBefore(thirtyDaysAgo);
//
//        for (Video video : oldVideos) {
//            String fileName = video.getRandomName();
//            File fileToDelete = new File(folderLocation + "/" + fileName);
//
//            if (fileToDelete.exists()) {
//                fileToDelete.delete();
//            }
//
//            videoRepository.delete(video);
//        }
//    }

    @Scheduled(cron = "0 0 0 * * ?") // This cron expression runs the method at midnight every day
    public void deactivateExpiredProperties() {
        List<Property> expiredProperties = propertyRepository.findByIsActiveTrueAndExpirationDateAfter(new Date());

        for (Property property : expiredProperties) {
            property.setActive(false);
            propertyRepository.save(property);
        }
    }
}
