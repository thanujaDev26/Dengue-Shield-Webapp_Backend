package com.dengue_webapp.dengue_webapp.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePatientDto {
    private String id; // Kept as String assuming NIC is manually entered
    private String name;
    private String address;
    private String notifierStatus; // Fixed casing
    private String religion;
    private String race;
    private String telephoneNumber;
    private String occupation;
    private String gender;
    private String nameOfNotifier; // Fixed casing
    private String Age;
}
