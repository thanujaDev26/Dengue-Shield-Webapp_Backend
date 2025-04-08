package com.dengue_webapp.dengue_webapp.dto.request;

import com.dengue_webapp.dengue_webapp.model.entity.CommunicableDiseaseNotification;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestNotebookDto {

    private long messageId;
    private long h544Id;
    private Patient  patient;
    private String distance;
    private String subject;
    private String condition;
    private String isolation;
    private String  termination;
    private String remarks;


}
