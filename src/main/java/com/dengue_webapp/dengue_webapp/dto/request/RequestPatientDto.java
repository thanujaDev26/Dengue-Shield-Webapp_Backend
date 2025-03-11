package com.dengue_webapp.dengue_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPatientDto {
    private String id; // Kept as String assuming NIC is manually entered
    private String name;
    private String address;
    private String religion;
    private String race;
    private String telephoneNumber;
    private String occupation;
    private String gender;
    private String guardianName;// Fixed casing
    private  Integer Age;
}

