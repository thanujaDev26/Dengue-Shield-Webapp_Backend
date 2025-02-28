package com.dengue_webapp.dengue_webapp.dto.request;

import com.dengue_webapp.dengue_webapp.model.entity.InwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.OutwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessageDto {

    private long mohOfficerId; // MOH who sent the message
    private String phiOfficerEmail; // PHI who received the message
    private long inwardDocumentId; // Linked inward document


}

