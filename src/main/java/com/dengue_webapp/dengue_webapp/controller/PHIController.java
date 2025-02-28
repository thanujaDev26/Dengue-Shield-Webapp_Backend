package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.InwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.Message;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/phi")
public class PHIController {

    @Autowired
    private  PHIService phiService;
    @PatchMapping("/updatePhi/{id}")
    public ResponseEntity<StandardResponse> updatePhiOfficer(@PathVariable Long id, @RequestBody Map<String ,Object> Updates) {
        //System.out.println("hello world");
        PHIOfficer UpdatedUser = phiService.updatePhiOfficer(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @PostMapping("/saveInwardDocument/{phiId}")
    public ResponseEntity<StandardResponse> saveInwardDocument(@PathVariable Long phiId,@RequestBody RequestInwardDocumentDto document) {

        InwardDocument savedDocument = phiService.saveInwardDocument( phiId,document);
        StandardResponse response = new StandardResponse(201, " user added successfully", savedDocument);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getAllInwardDocument/{phiId}")
    public ResponseEntity<StandardResponse> getAllInwardDocument(@PathVariable Long phiId) {

        List<InwardDocument> savedDocumentList = phiService.getAllInwardDocument(phiId);
        StandardResponse response = new StandardResponse(200, "Fetched all  users", savedDocumentList);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateInwardDocument/{id}")
    public ResponseEntity<StandardResponse> updateInwardDocument(@PathVariable(value = "id")  Long id, @RequestBody Map<String ,Object> Updates) {

        InwardDocument updatedDocument = phiService.updateInwardDocument(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",updatedDocument);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/deleteInwardDocument/{id}")
    public ResponseEntity<StandardResponse> deleteInwardDocument(@PathVariable Long id) {
        InwardDocument deletedDocument = phiService.deleteInwardDocument(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",deletedDocument);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //get all the pending messages.

    @GetMapping("/getAllPendingMessages/{phiId}")
    public ResponseEntity<StandardResponse> getAllPendingMessages(@PathVariable Long phiId) {

        List<Message> pendingMessageList = phiService.getAllPendingMessages(phiId);
        StandardResponse response = new StandardResponse(200, "Fetched all  users",pendingMessageList);
        return ResponseEntity.ok(response);
    }
      //change the status of mesaage to sent.

     @PatchMapping("/{id}/status")
     public ResponseEntity<StandardResponse> updateMessageStatus(@PathVariable Long id) {
         //System.out.println("hello world");
         Message updatedMessage = phiService.updateMessageStatus(id);
         StandardResponse response = new StandardResponse(200, "user updated successfully",updatedMessage);
         return ResponseEntity.status(HttpStatus.OK).body(response);

     }








}
