package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestNotebookDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.exceptions.DataAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.model.entity.*;
import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import com.dengue_webapp.dengue_webapp.repository.*;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PHIServiceImpl implements PHIService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  PHIRepo phiRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private MOHRepo mohRepo;

     @Autowired
     private MessageRepo messageRepo;

     @Autowired
     private CommunicableDiseaseNotificationRepo communicableDiseaseNotificationRepo;

     @Autowired
     private  NoteBookRepo noteBookRepo;

    @Autowired
    private InwardDocumentRepo inwardDocumentRepo;
    @Override
    public PHIOfficer createPHI(RequestPHIDto dto){
        try {
            String email = dto.getEmail();

            // Check if email already exists
            if (!phiRepo.existsByAppuser_Email(email)) {
                PHIOfficer newPhiOfficer = modelMapper.map(dto, PHIOfficer.class);
                assignMOHOfficer(newPhiOfficer,dto.getDistrict(), dto.getBranch());
                return phiRepo.save(newPhiOfficer); // Save and return the new officer
            } else {
                System.out.println("PHI Officer with this email already exists!");
                return null; // Return null if officer already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Ensure method always returns a value
        }
    }

    public void assignMOHOfficer(  PHIOfficer newPhiOfficer,String branch,String district){
        MOHOfficer mohOfficer = mohRepo.findByDistrictAndBranch(district,branch);
        if(mohOfficer != null){
            newPhiOfficer.setMohOfficer(mohOfficer);
        }

    }


    @Override
    public ResponsePHIDto getPHI(long id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();
        return  modelMapper.map(phi,ResponsePHIDto.class);

    }

    @Override
    public void deletePHI(long id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();

    }


    @Override
    public List<ResponsePHIDto> getAllPHI(String searchText, int page, int size) {
        if(searchText == null || searchText.isEmpty()){
            searchText = "%%";
        }else{
            searchText = "%" + searchText + "%";
        }

        List<PHIOfficer> phis = phiRepo.searchPHIOfficer(searchText, PageRequest.of(page, size));
        List<ResponsePHIDto> dtos = new ArrayList<>();
        phis.forEach(phi ->{

            dtos.add(modelMapper.map(phi, ResponsePHIDto.class));

        });

        return dtos;
    }

    @Override
    public PHIOfficer updatePhiOfficer(Long id, Map<String, Object> updates) {
        PHIOfficer userToUpdate = phiRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("phi Officer not found. Please register the user."));
        updates.forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "name":
                    userToUpdate.getAppuser().setName((String)value);
                    break;
                case "email":
                    userToUpdate.getAppuser().setEmail((String)value);
                    break;

                case "mobilenumber":
                    userToUpdate.setMobilenumber(((String) value));
                    break;
                case "district":
                    userToUpdate.setDistrict(((String) value).toLowerCase());
                    break;
                case "area":
                    userToUpdate.setArea(((String) value).toLowerCase());
                    break;
                case "branch":
                    userToUpdate.setBranch(((String) value).toLowerCase());
                    break;
                default:
                    throw new InvalidArgumentExeception("Invalid field name: " + key);
            }
        });
        //`assignPHIOfficers(userToUpdate.getId(), userToUpdate.getBranch(), userToUpdate.getDistrict());
        return phiRepo.save(userToUpdate);
    }

    @Override
    public InwardDocument saveInwardDocument(Long phiId, RequestInwardDocumentDto document) {
        PHIOfficer phi = phiRepo.findById(phiId)
                .orElseThrow(() -> new NoDataFoundException("Phi not found"));

        if (inwardDocumentRepo.existsByOriginalNumberAndInwardNumber(document.getOriginalNumber(), document.getInwardNumber())) {
            throw new DataAlreadyExistsException("Inward document already exists");
        }

        InwardDocument newDocument = modelMapper.map(document, InwardDocument.class);
        newDocument.setPhi(phi); // Link inward document to PHI officer

//        phi.getInwardDocuments().add(newDocument); // Safe to add without null checks
        phiRepo.save(phi); // Cascade saves the newDocument too

        return newDocument;
    }

    @Override
    public List<InwardDocument> getAllInwardDocument(Long phiId) {
        List<InwardDocument> documentList = inwardDocumentRepo.findAllByPhi_Id(phiId);
        if(documentList.isEmpty()){
            throw  new NoDataFoundException("no inward documents are added ");
        }
        return documentList;
    }

    @Override
    public InwardDocument updateInwardDocument(Long id, Map<String, Object> updates) {
        return null;
    }
    public InwardDocument deleteInwardDocument(Long id) {
        Optional<InwardDocument> existingDocument = inwardDocumentRepo.findById(id);
        if (existingDocument.isEmpty()) {
            throw new NoDataFoundException("Inward document is not found");
        }
        inwardDocumentRepo.deleteById(id);
        return existingDocument.get();
    }

    @Override
    public List<Message> getAllPendingMessages(Long phiId) {
        //System.out.println("hello");
        List<Message> pendingMessageList = messageRepo.findAllByPhiOfficer_IdAndStatusEquals(phiId,MessageStatus.PENDING);
        System.out.println(pendingMessageList);
//        if(pendingMessageList.isEmpty()){
//            throw  new NoDataFoundException("there is no  message for you..");
//        }

        return pendingMessageList;

    }

    @Override
    public Message updateMessageStatus(Long id) {
        Optional<Message> oldMessage = messageRepo.findById(id);
        if(oldMessage.isEmpty()){
            throw new NoDataFoundException("No message can't be found");
        }
        oldMessage.get().setStatus(MessageStatus.SENT);

        return messageRepo.save(oldMessage.get());
    }

    @Override
    public List<Message> getAllSentMessages(Long phiId) {
        List<Message> sentMessageList = messageRepo.findAllByPhiOfficer_IdAndStatusEquals(phiId,MessageStatus.SENT);
       // System.out.println(pendingMessageList);
        if(sentMessageList.isEmpty()){
            throw  new NoDataFoundException("there is no  message for you..");
        }

        return sentMessageList;
    }

    @Override
    public Message getMessageById(Long messageId) {
        Optional<Message> oldMessage = messageRepo.findById(messageId);
        if(oldMessage.isEmpty()){
            throw new NoDataFoundException("No message can't be found");
        }

        return oldMessage.get();
    }

    @Override
    public NoteBook saveNotebook(Long phiId, RequestNotebookDto note) {
        //System.out.println("checkpoint 1");
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(phiId);
        //System.out.println(phiOfficer.get());
        if (phiOfficer.isEmpty()){
            throw new NoDataFoundException("phi officer is not found,please register...");
        }
       // System.out.println("checkpoint 2");
        Optional<Patient> existingPatient = patientRepo.findById(note.getPatient().getId());
        //System.out.println(existingPatient.get());
        if (existingPatient.isEmpty()) {
            throw new NoDataFoundException("Patient not found");
        }
        Patient patientToUpdate = existingPatient.get();
        patientToUpdate.setOccupation(note.getPatient().getOccupation());
        patientToUpdate.setReligion(note.getPatient().getReligion());
        patientToUpdate.setRace(note.getPatient().getRace());
        patientRepo.save(patientToUpdate);

        //System.out.println(patientToUpdate);

        Optional<CommunicableDiseaseNotification> h544 = communicableDiseaseNotificationRepo.findById(note.getH544Id());
        if (h544.isEmpty()){
            throw new NoDataFoundException("h544 is not found");
        }

      //  System.out.println(h544);

        NoteBook newNote  = modelMapper.map(note, NoteBook.class);
        //System.out.println( newNote);
        newNote.setH544(h544.get());
       // System.out.println( newNote);
        newNote.setPhiOfficer(phiOfficer.get());
        //System.out.println( newNote);
        try{ noteBookRepo.save( newNote);}
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //System.out.println( newNote);
        return  newNote;
    }


}