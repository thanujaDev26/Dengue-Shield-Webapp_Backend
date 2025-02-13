package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.*;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseMOHDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.InwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
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
@RequestMapping("/api/v1/moh")
public class MOHController {

    @Autowired
    private MOHService mohService;

    @Autowired
    private ModelMapper modelMapper;

    //we pass moh id to frontend when moh login.
    @PatchMapping("/updateMoh/{id}")
    public ResponseEntity<StandardResponse> updateMohOfficer(@PathVariable(value = "id")  Long id, @RequestBody Map<String ,Object> Updates) {
        MOHOfficer UpdatedUser = mohService.updateMohOfficer(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/assignPhiOfficers")
    public ResponseEntity<StandardResponse> assignPHIOfficers (@RequestBody RequestMOhInfo mohInfo){
        List<PHIOfficer> phiOfficerList = mohService.assignPHIOfficers(mohInfo.getId(),mohInfo.getBranch(),mohInfo.getDistrict());
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

        ResponseDiseaseNotificationDto responseNotification = mohService.saveDiseaseNotification(notification);
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
    public ResponseEntity<StandardResponse> updateDiseaseNotificationByNic(@PathVariable(value = "id") String id, @RequestBody Map<String ,Object> Updates) {

        ResponseDiseaseNotificationDto diseaseResponse = mohService.updateDiseaseNotificationByNic(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully", diseaseResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/deleteDiseaseNotification/{id}")
    public ResponseEntity<StandardResponse> deleteDiseaseNotificationById(@PathVariable String id) {
        ResponseDiseaseNotificationDto DeletedUser = mohService.deleteDiseaseNotificationById(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/saveInwardDocument")
    public ResponseEntity<StandardResponse> saveInwardDocument(@RequestBody RequestInwardDocumentDto document) {

        InwardDocument savedDocument = mohService.saveInwardDocument( document);
        StandardResponse response = new StandardResponse(201, " user added successfully", savedDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getAllInwardDocument")
    public ResponseEntity<StandardResponse> getAllInwardDocument() {

        List<InwardDocument> usavedDocumentList = mohService.getAllInwardDocument();
        StandardResponse response = new StandardResponse(200, "Fetched all  users", usavedDocumentList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getInwardDocument/{id}")
    public ResponseEntity<StandardResponse> getInwardDocumentById(@PathVariable  Long id) {

        InwardDocument savedDocument = mohService.getInwardDocumentById(id);
        StandardResponse response = new StandardResponse(200, " user fetched successfully", savedDocument);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @PatchMapping("/updateAppUser/{id}")
    public ResponseEntity<StandardResponse> updateInwardDocument(@PathVariable(value = "id")  Long id, @RequestBody Map<String ,Object> Updates) {

        InwardDocument updatedDocument = mohService.updateInwardDocument(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",updatedDocument);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/deleteInwardDocument/{id}")
    public ResponseEntity<StandardResponse> deleteInwardDocument(@PathVariable Long id) {
        InwardDocument deletedDocument = mohService.deleteInwardDocument(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",deletedDocument);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/sendInwardDocument")

    public ResponseEntity<StandardResponse> sendInwardDocument(@RequestBody RequestInwardDocumentDto document) {

        InwardDocument savedDocument = mohService.saveInwardDocument( document);
        StandardResponse response = new StandardResponse(201, " user added successfully", savedDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
/*
*





*
*  */




//crud will add lastly..




