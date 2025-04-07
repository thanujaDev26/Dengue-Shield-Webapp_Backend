package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestNotebookDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.*;
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
    @PatchMapping("/updateUser/{id}")
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
        StandardResponse response = new StandardResponse(200, "Fetched all the messages",pendingMessageList);
        return ResponseEntity.ok(response);
    }
      //change the status of mesaage to sent.
      @GetMapping("/getAllSentMessages/{phiId}")
      public ResponseEntity<StandardResponse> getAllSentMessages(@PathVariable Long phiId) {

          List<Message> pendingMessageList = phiService.getAllSentMessages(phiId);
          StandardResponse response = new StandardResponse(200, "Fetched all the messages",pendingMessageList);
          return ResponseEntity.ok(response);
      }
     @PatchMapping("/updateMessageStatus/{id}")
     public ResponseEntity<StandardResponse> updateMessageStatus(@PathVariable Long id) {
         //System.out.println("hello world");
         Message updatedMessage = phiService.updateMessageStatus(id);
         StandardResponse response = new StandardResponse(200, "message updated successfully",updatedMessage);
         return ResponseEntity.status(HttpStatus.OK).body(response);

     }

    @GetMapping("/getMessage/{messageId}")
    public ResponseEntity<StandardResponse> getMessageById(@PathVariable Long messageId) {

        Message message = phiService.getMessageById(messageId);
        StandardResponse response = new StandardResponse(200, "Fetched the message Sucessfully",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveNotebook/{phiId}")
    public ResponseEntity<StandardResponse> saveNotebook(@PathVariable Long phiId, @RequestBody RequestNotebookDto note) {
        System.out.println("hello in controller");
        NoteBook newNote = phiService.saveNotebook( phiId,note);
        StandardResponse response = new StandardResponse(201, "Note saved succesfully", newNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getAllCompletedMessages/{phiId}")
    public ResponseEntity<StandardResponse> getAllCompletedMessages(@PathVariable Long phiId) {

        List<Message> completedMessageList = phiService.getAllCompletedMessages(phiId);
        StandardResponse response = new StandardResponse(200, "Fetched all the messages",completedMessageList);
        return ResponseEntity.ok(response);
    }



}
