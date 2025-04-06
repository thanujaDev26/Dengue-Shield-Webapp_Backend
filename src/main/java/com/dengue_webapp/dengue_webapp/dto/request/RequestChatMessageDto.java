package com.dengue_webapp.dengue_webapp.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RequestChatMessageDto {

    private Long senderId;


    private List<Long> receiverId;


    private String content;


    private LocalDateTime timestamp;


    private boolean readstatus;

}
