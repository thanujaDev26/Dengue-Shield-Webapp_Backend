package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MOHOfficer")
public class MOHOfficer {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appuser;

    @Column(name = "mobilenumber", length = 255)
    private String mobilenumber;

    @Column(name = "district", length = 255)
    private String district;

    @Column(name = "branch", length = 255)
    private String branch;

    // ✅ One MOH Officer manages multiple PHI officers
    @OneToMany(mappedBy = "mohOfficer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PHIOfficer> phiOfficers = new ArrayList<>();

    // ✅ One MOH Officer can create multiple notifications
    @OneToMany(mappedBy = "mohOfficer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunicableDiseaseNotification> notifications = new ArrayList<>();


}
