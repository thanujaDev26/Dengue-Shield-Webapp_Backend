package com.dengue_webapp.dengue_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestEventDto {
    private String title;
    private String message;
    private String type;
    private LocalDateTime date;
    private String venue;

}
