package com.dengue_webapp.dengue_webapp.model.entity;

import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PHIOfficer")
public class PHIOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appuser;

    @Column(name = "mobilenumber", length = 255)
    private String mobilenumber;


    @Column(name = "area", length = 255)
    private String area;


    @Column(name = "district", length = 255)
    private String district;

    @Column(name = "branch", length = 255)
    private String branch;

    @ManyToOne
@JoinColumn(name = "moh_officer_id", nullable = false)
private MOHOfficer mohOfficer;

    
}
