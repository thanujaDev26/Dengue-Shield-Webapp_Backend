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

    @Column(name = "guardian_name", nullable = false, length = 100) // Added length
    private String guardianName;

    @Column(nullable = false, length = 100)
    private String name;

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

    @Column(name = "medical_officer", nullable = false, length = 100)
    private String medicalOfficer;  // Fixed variable name

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false) // Foreign key reference
    private Patient patient;

}
