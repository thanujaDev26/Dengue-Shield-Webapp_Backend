package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestMOHDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.InwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;

import java.util.List;
import java.util.Map;

public interface MOHService {

    List<PHIOfficer> assignPHIOfficers(Long id, String branch, String district);

    MOHOfficer updateMohOfficer(Long id, Map<String, Object> updates);

    List<PHIOfficer> getAppUserById(Long id);

    ResponseDiseaseNotificationDto getDiseaseNotificationByPateintId(String id);

    ResponseDiseaseNotificationDto saveDiseaseNotification(RequestDiseaseNotificationDto notification);

    List<ResponseDiseaseNotificationDto> getAllNotifications();

    ResponseDiseaseNotificationDto updateDiseaseNotificationByNic(String id, Map<String, Object> updates);

    ResponseDiseaseNotificationDto deleteDiseaseNotificationById(String id);

    InwardDocument saveInwardDocument(RequestInwardDocumentDto document);

    List<InwardDocument> getAllInwardDocument();

    InwardDocument getInwardDocumentById(Long id);

    InwardDocument updateInwardDocument(Long id, Map<String, Object> updates);

    InwardDocument deleteInwardDocument(Long id);
}
