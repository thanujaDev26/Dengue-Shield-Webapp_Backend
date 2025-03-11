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

    ResponseDiseaseNotificationDto updateDiseaseNotificationByNic(String id, Map<String, Object> updates);

    ResponseDiseaseNotificationDto deleteDiseaseNotificationById(String id);


    Message sendDiseaseNotification(RequestMessageDto message);
}
