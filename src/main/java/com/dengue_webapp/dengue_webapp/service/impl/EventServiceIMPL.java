package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestEventDto;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.model.entity.Events;
import com.dengue_webapp.dengue_webapp.repository.EventRepo;
import com.dengue_webapp.dengue_webapp.service.EventService;
import com.dengue_webapp.dengue_webapp.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventServiceIMPL implements EventService {


    @Autowired
    private EventRepo eventRepo;


    @Autowired
    private S3Service s3Service;
    @Override
    public Events saveNews(String title, String message, String type, LocalDateTime date, String venue, MultipartFile image) throws IOException {
        System.out.println("in impl");
        Events event1 = new Events();
        event1.setTitle(title);
        event1.setMessage(message);
        event1.setType(type);
        event1.setDate(date);
        event1.setVenue(venue);
        event1.setCreatedAt(LocalDateTime.now());
        event1.setUpdatedAt(LocalDateTime.now());
        System.out.println(image);
        System.out.println(event1);
        // Upload image to S3 if provided
        if (image != null ) {
            String imageUrl = s3Service.uploadFile(image);
            System.out.println(imageUrl);
            event1.setImageUrls(imageUrl);
            System.out.println(event1);
        }
        else{
            throw new NoDataFoundException("image is not included");
        }
        System.out.println(event1);
        Events savedEvent = eventRepo.save(event1);
        System.out.println(savedEvent); // EventId should not be null here
        return savedEvent;
    }


}

