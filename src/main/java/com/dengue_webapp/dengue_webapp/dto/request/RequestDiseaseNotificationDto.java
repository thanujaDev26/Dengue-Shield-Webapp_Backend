package com.dengue_webapp.dengue_webapp.dto.request;

import com.dengue_webapp.dengue_webapp.dto.response.ResponsePatientDto;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDiseaseNotificationDto {
    private String labResults;
    private LocalDate dateOfOnset;
    private LocalDate dateOfAdmission;
    private String institute;
    private String ward;
    private String bedNumber;
    private String nameOfNotifier;
    private String notifierStatus;
    private String diseaseName;
    private long mohOfficerId;
    private ResponsePatientDto patient;
}
