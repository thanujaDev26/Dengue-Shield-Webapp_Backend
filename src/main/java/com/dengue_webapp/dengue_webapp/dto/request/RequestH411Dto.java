package com.dengue_webapp.dengue_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestH411Dto {
    private String diseaseAsNotifiedDate;
    private String diseaseConfirmDate;
    private String dateOfDischarge;
    private String natureOfCase;
    private String laboratoryFindings;
    private String outcome;
    private List<String> familyMembers;
}
