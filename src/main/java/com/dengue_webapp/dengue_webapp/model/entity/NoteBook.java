package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"phi_id", "h544_id"}))
public class NoteBook {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "h544_id", nullable = false)
    private CommunicableDiseaseNotification h544;


    @OneToOne
    @JoinColumn(name = "phi_id", nullable = false,unique = false)
    private PHIOfficer phiOfficer;

    @Column(name = "distance")
    private String distance;

    @Column(name = "subject")
    private String subject;


    @Column(name = "house_condition")
    private String condition;

    @Column(name = "isolation")
    private String isolation;

    @Column(name = "termination")
    private String  termination;

    @Column(name = "remarks")
    private String remarks;


    @Column(name = "createdAt",nullable = true)
    private Date createdAt ;

    @Column(name = "updatedAt",nullable = true)
    private Date updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();  // Set the createdAt timestamp before inserting
    }

}
