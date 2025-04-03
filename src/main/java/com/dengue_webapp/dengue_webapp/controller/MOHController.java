package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.*;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseMOHDto;
import com.dengue_webapp.dengue_webapp.model.entity.*;
import com.dengue_webapp.dengue_webapp.service.MOHService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/moh")
public class MOHController {

    @Autowired
    private MOHService mohService;

    @Autowired
    private ModelMapper modelMapper;

    //we pass moh id to frontend when moh login.
    @PatchMapping("/updateMoh/{id}")
    public ResponseEntity<StandardResponse> updateMohOfficer(@PathVariable Long id, @RequestBody Map<String ,Object> Updates) {
       // System.out.println("hello world");
        MOHOfficer UpdatedUser = mohService.updateMohOfficer(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/assignPhiOfficers")
    public ResponseEntity<StandardResponse> assignPHIOfficers (@RequestBody RequestMOhInfo mohInfo){
        List<PHIOfficer> phiOfficerList = mohService.assignPHIOfficers(mohInfo);
        StandardResponse response = new StandardResponse(200, "user updated successfully",phiOfficerList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/viewAllAssigendPhiOfficers/{id}")
    public ResponseEntity<StandardResponse> viewPhiOfficersById(@PathVariable  Long id) {
        List<PHIOfficer> phiOfficerList  = mohService.getAppUserById(id);
        StandardResponse response = new StandardResponse(200, " PHis fetched successfully", phiOfficerList);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }


    @GetMapping("/getDiseaseNotification/{id}")
    public ResponseEntity<StandardResponse> getDiseaseNotificationByPateintId(@PathVariable  String id) {
        ResponseDiseaseNotificationDto diseaseNotification  = mohService.getDiseaseNotificationByPateintId(id);
        StandardResponse response = new StandardResponse(200, " notification fetched successfully", diseaseNotification);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @PostMapping("/saveDiseaseNotification")
    public ResponseEntity<StandardResponse> saveDiseaseNotification(@RequestBody RequestDiseaseNotificationDto notification) {
        System.out.println("in controller");
        CommunicableDiseaseNotification responseNotification = mohService.saveDiseaseNotification(notification);
        StandardResponse response = new StandardResponse(201, " notification  added successfully", responseNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getAllDiseaseNotification")
    public ResponseEntity<StandardResponse> getAllDiseaseNotification() {

        List<ResponseDiseaseNotificationDto> notifyList =  mohService.getAllNotifications();
        StandardResponse response = new StandardResponse(200, "get all  Notificattions successfully", notifyList);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateDiseaseNotification/{id}")
    public ResponseEntity<StandardResponse> updateDiseaseNotificationByNic(@PathVariable(value = "id") long id, @RequestBody Map<String ,Object> Updates) {

        ResponseDiseaseNotificationDto diseaseResponse = mohService.updateDiseaseNotificationById(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully", diseaseResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/deleteDiseaseNotification/{id}")
    public ResponseEntity<StandardResponse> deleteDiseaseNotificationById(@PathVariable String id) {
        ResponseDiseaseNotificationDto DeletedUser = mohService.deleteDiseaseNotificationById(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/sendDiseaseNotification")

    public ResponseEntity<StandardResponse> sendDiseaseNotification(@RequestBody RequestMessageDto message) {
        Message savedMessage = mohService.sendDiseaseNotification(message);
        StandardResponse response = new StandardResponse(200, "message sent succesfully",savedMessage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/getAllmessages/{id}")
    public ResponseEntity<StandardResponse> getAllmessagesById(@PathVariable  long id) {

        List<Message> messageList =  mohService.getAllMessagesById(id);
        StandardResponse response = new StandardResponse(200, "get all  messages successfully", messageList );
        return ResponseEntity.ok(response);
    }


}
/*
*





*
*  */




//crud will add lastly..




