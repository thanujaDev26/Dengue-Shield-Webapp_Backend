package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "OutwardDocument")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutwardDocument {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="number",nullable = false)
    private String number;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    @Column(name="subject",nullable = false)
    private String subject;

    @Column(name="dateofanswer")
    private LocalDate dateofanswer;

    @Column(name="remarks",nullable = false)
    private String remarks;


    @ManyToOne
    @JoinColumn(name = "phi_id", nullable = false)
    private PHIOfficer phi;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now(); // ✅ Ensures accurate date when saving
    }

}
