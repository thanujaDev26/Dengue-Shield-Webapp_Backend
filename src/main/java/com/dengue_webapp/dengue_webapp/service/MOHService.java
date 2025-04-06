package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.*;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.model.entity.*;

import java.util.List;
import java.util.Map;

public interface MOHService {

    List<PHIOfficer> assignPHIOfficers(RequestMOhInfo mohinfo);

    MOHOfficer updateMohOfficer(Long id, Map<String, Object> updates);

    List<PHIOfficer> getAppUserById(Long id);

    ResponseDiseaseNotificationDto getDiseaseNotificationByPateintId(String id);

    CommunicableDiseaseNotification saveDiseaseNotification(RequestDiseaseNotificationDto notification);

    List<ResponseDiseaseNotificationDto> getAllNotifications();

    ResponseDiseaseNotificationDto updateDiseaseNotificationById(long id, Map<String, Object> updates);

    ResponseDiseaseNotificationDto deleteDiseaseNotificationById(String id);


    Message sendDiseaseNotification(RequestMessageDto message);

    List<Message> getAllMessagesById(long id);

    String deleteMessageById(long id);

    List<PHIOfficer> getAllPhisId(long id);

    String updatePhi(long mohId, long phiId);

    String unassignPhi(long id);
}
