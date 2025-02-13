package com.dengue_webapp.dengue_webapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patient")
public class Patient {

    @Id
    private String nic; // Kept as String assuming NIC is manually entered

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) // Removed unique = true from address
    private String address;

    @Column(nullable = false)
    private String notifierStatus; // Fixed casing

    @Column(nullable = false)
    private String religion;

    @Column(nullable = false)
    private String race;

    @Column(nullable = false)
    private String telephoneNumber;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String nameOfNotifier; // Fixed casing

    @Column(nullable = false, updatable = false)
    private LocalDate dateOfBirth; // Changed to LocalDate
}
