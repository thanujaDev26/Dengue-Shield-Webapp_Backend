package com.dengue_webapp.dengue_webapp.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestInwardDocumentDto {
    private long phiId;
    private String originalNumber;
    private String inwardNumber;
    private LocalDate date;
    private String fromWhome;
    private String subject;
    private LocalDate dateofanswer;
    private String remarks;
}
