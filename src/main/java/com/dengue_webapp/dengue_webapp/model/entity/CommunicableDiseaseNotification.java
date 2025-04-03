package com.dengue_webapp.dengue_webapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "CommunicableDiseaseNotification") // Fixed typo
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicableDiseaseNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 45)
    private Long id;

    @Column(name = "lab_results", length = 100)
    private String labResults;  // Fixed variable name

    @Column(name = "date_of_onset", nullable = false)
    private LocalDate dateOfOnset;

    @Column(name = "date_of_admission", nullable = false)
    private LocalDate dateOfAdmission;

    @Column(nullable = false, length = 150)
    private String institute;

    @Column(nullable = false, length = 100)
    private String ward;

    @Column(name = "bed_number", nullable = false, length = 50)
    private String bedNumber;


    @Column(nullable = false)
    private String nameOfNotifier; // Fixed casing

    @Column(nullable = false)
    private String notifierStatus; // Fixed casing

    @Column(nullable = false)
    private String diseaseName;

    @ManyToOne
    @JoinColumn(name = "moh_id", nullable = false)
    private MOHOfficer mohOfficer;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false,unique = false) // Foreign key reference
    private Patient patient;

    public CommunicableDiseaseNotification(String labResults, LocalDate dateOfOnset, LocalDate dateOfAdmission,
                                           String institute, String ward, String bedNumber,
                                           String nameOfNotifier, String notifierStatus,
                                           String diseaseName, MOHOfficer mohOfficer,
                                           Patient patient) {
        this.labResults = labResults;
        this.dateOfOnset = dateOfOnset;
        this.dateOfAdmission = dateOfAdmission;
        this.institute = institute;
        this.ward = ward;
        this.bedNumber = bedNumber;
        this.nameOfNotifier = nameOfNotifier;
        this.notifierStatus = notifierStatus;
        this.diseaseName = diseaseName;
        this.mohOfficer = mohOfficer;
        this.patient = patient;
    }




}

/* */
