package com.dengue_webapp.dengue_webapp.service;


import com.dengue_webapp.dengue_webapp.dto.request.RequestEventDto;
import com.dengue_webapp.dengue_webapp.model.entity.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public interface EventService {

    Events saveNews(String title, String message, String type, LocalDateTime date, String venue, MultipartFile image) throws IOException;


}
