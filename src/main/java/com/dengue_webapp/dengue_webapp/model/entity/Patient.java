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
    private String id; // Kept as String assuming NIC is manually entered

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) // Removed unique = true from address
    private String address;

    @Column(nullable = true)
    private String religion;

    @Column(nullable = true)
    private String race;

    @Column(nullable = true)
    private String telephoneNumber;

    @Column(nullable = true)
    private String occupation;

    @Column(nullable = true)
    private String gender;

    @Column(name = "guardian_name", nullable = false, length = 100) // Added length
    private String guardianName;

    @Column(nullable = false)
    private Integer age; // Changed to LocalDate
}
