package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "note_book")
public class NoteBook {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "h544_id", nullable = false)
    private CommunicableDiseaseNotification h544;


    @OneToOne
    @JoinColumn(name = "phi_id", nullable = false)
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
    private Date createdAt;

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
/* date: location.state.date || '',
        day: location.state.day || '',
        time: location.state.time || '',
        timePeriod: location.state.timePeriod || 'AM',
        dutyPlace: location.state.dutyPlace || '',
        outgoingTime: location.state.outgoingTime || '',
        outgoingTimePeriod: location.state.outgoingTimePeriod || 'AM',
        ingoingTime: location.state.ingoingTime || '',
        ingoingTimePeriod: location.state.ingoingTimePeriod || 'AM',
        fieldOutTime: location.state.fieldOutTime || '',
        fieldInTime: location.state.fieldInTime || '',
        distance: location.state.distance || '',
        vehicleOption: location.state.vehicleOption || 'Public Transport',
        religion: location.state.religion || '',
        race: location.state.race || '',
        job: location.state.job || '', */