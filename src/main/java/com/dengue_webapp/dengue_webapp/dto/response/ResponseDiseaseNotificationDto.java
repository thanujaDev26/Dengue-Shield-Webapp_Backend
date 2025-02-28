package com.dengue_webapp.dengue_webapp.dto.response;

import com.dengue_webapp.dengue_webapp.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDiseaseNotificationDto {

    private String guardianName;
    private String name;
    private String labResults;
    private LocalDate dateOfOnset;
    private LocalDate dateOfAdmission;
    private String institute;
    private String ward;
    private String bedNumber;
    private String medicalOfficer;
    private ResponsePatientDto patient;
}
