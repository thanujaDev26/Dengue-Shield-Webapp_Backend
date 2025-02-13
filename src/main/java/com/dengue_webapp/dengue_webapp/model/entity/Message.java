package com.dengue_webapp.dengue_webapp.model.entity;


import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "moh_officer_id", nullable = false)
    private MOHOfficer mohOfficer; // MOH who sent the message

    @ManyToOne
    @JoinColumn(name = "phi_officer_id", nullable = false)
    private PHIOfficer phiOfficer; // PHI who received the message

    @OneToOne
    @JoinColumn(name = "inward_document_id", nullable = false)
    private InwardDocument inwardDocument; // Linked inward document

    @OneToOne
    @JoinColumn(name = "outward_document_id", nullable = true)
    private OutwardDocument outwardDocument; // Linked outward document (nullable because it is sent later)

    @Enumerated(EnumType.STRING)
    private MessageStatus status; // SENT, PENDING, COMPLETED

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;
}
