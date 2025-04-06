package com.dengue_webapp.dengue_webapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class H411 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diseaseAsNotifiedDate;
    private String diseaseConfirmDate;
    private String dateOfDischarge;
    private String natureOfCase;
    private String laboratoryFindings;
    private String outcome;

    @ElementCollection
    @CollectionTable(name = "family_members", joinColumns = @JoinColumn(name = "h411_id"))
    @Column(name = "family_member")
    private List<String> familyMembers;
}
