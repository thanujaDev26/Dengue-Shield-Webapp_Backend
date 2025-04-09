package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestEventDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.Events;
import com.dengue_webapp.dengue_webapp.service.EventService;
import com.dengue_webapp.dengue_webapp.service.S3Service;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {


    private final S3Service s3Service;
   @Autowired
   private EventService eventService;

    @PostMapping("/saveNews")
    public ResponseEntity<StandardResponse> saveNews(@RequestParam("title") String title,
                                                     @RequestParam("message") String message,
                                                     @RequestParam("type") String type,
                                                     @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                                     @RequestParam("venue") String venue,
                                                     @RequestParam("image") MultipartFile image) throws IOException {
        System.out.println("hello in controller!");
      Events event1 = eventService.saveNews(title,message,type,date,venue,image);
        StandardResponse response = new StandardResponse(201, "Event saved suceesfully ", event1);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }


}
