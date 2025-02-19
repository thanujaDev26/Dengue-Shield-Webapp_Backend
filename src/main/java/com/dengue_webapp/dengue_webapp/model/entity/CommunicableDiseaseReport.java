package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CommunicableDiseaseReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 45)
    private Long id;

    @Column(name = "disease_as_notified",nullable = false)
    private String diseaseAsNotified;

    @Column(name = "disease_confirmed",nullable = false)
    private String diseaseConfirmed;

    @Column(name="patientName",nullable = false)
    private String patientName;

    @Column(name="sex",nullable = false)
    private String sex;

    @Column(name="ethnicGroup",nullable = false)
    private String ethnicGroup;

    @Column(name = "date_of_onset", nullable = false)
    private LocalDate dateOfOnset;

    @Column(name = "date_of_hospitalization", nullable = false)
    private LocalDate dateOfHospitalization;

    @Column(name = "date_of_discharge", nullable = false)
    private LocalDate dateOfdischarge;

    @Column(name="hospitalName",nullable = false)
    private String hospitalName;

    @Column(name="outcome",nullable = false)
    private String outcome;

    @Column(name="isolation_place",nullable = false)
    private String isolationPlace;

    @Column(name="natureOfCase",nullable = false)
    private String natureOfCase;

    @Column(name="one_case_in_outbreak",nullable = false)
    private String oneCaseInOutBreak;

    @Column(name="patientMovements",nullable = false)
    private String patientsMovements;

    @Column(name="laboratoryEnding")
    private String laboratoryEnding;

    @OneToMany(mappedBy = "h411", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<InvestigatedContact> investigatedContacts;



}
