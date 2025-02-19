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
public class InvestigatedContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private String age;

    @Column(name = "date_of_seen", nullable = false)
    private LocalDate dateOfSeen;

    @Column(name="observation")
    private String observation;


    @ManyToOne
    @JoinColumn(name = "h411_id", nullable = false)
    private CommunicableDiseaseReport h411;
}
