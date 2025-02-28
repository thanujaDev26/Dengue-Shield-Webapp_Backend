package com.dengue_webapp.dengue_webapp.model.entity;

import com.dengue_webapp.dengue_webapp.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PreApprovedUsers")
//When a PHI or MOH tries to register, their email must match an entry in this table.
public class PreApprovedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // MOH, PHI

    // Getters and Setters
}

