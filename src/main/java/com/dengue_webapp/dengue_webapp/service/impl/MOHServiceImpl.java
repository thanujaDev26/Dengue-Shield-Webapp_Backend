package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestMOHDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePatientDto;
import com.dengue_webapp.dengue_webapp.exceptions.DataAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.*;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.*;
import com.dengue_webapp.dengue_webapp.service.MOHService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class MOHServiceImpl implements MOHService {
    @Autowired
    private MOHRepo mohRepo;

    @Autowired
    private PHIRepo phiRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private CommunicableDiseaseNotificationRepo communicableDiseaseNotificationRepo;


    @Autowired
    private InwardDocumentRepo inwardDocumentRepo;

    @Override
    public List<PHIOfficer> assignPHIOfficers(Long id, String branch, String district) {
        // Fetch MOHOfficer and handle if not found
        MOHOfficer mohOfficer = mohRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register or provide a correct ID."));
        List<PHIOfficer> phiOfficers = phiRepo.findAllByDistrictAndBranch(district, branch);
        if (phiOfficers.isEmpty()) {
            throw new NoDataFoundException("No PHI Officers found in the given district and branch. Please register PHI Officers.");
        }
        mohOfficer.setPhiOfficers(phiOfficers);
        mohRepo.save(mohOfficer);
        return phiOfficers;
    }


    @Override
    public MOHOfficer updateMohOfficer(Long id, Map<String, Object> updates) {

            // Fetch MOHOfficer, throw an exception if not found
            MOHOfficer userToUpdate = mohRepo.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register the user."));
            updates.forEach((key, value) -> {
                switch (key.toLowerCase()) {
                    case "mobilenumber":
                        userToUpdate.setMobilenumber((String) value);
                        break;
                    case "district":
                        userToUpdate.setDistrict((String) value);
                        break;
                    case "branch":
                        userToUpdate.setBranch((String) value);
                        break;
                    default:
                        throw new InvalidArgumentExeception("Invalid field name: " + key);
                }
            });
            assignPHIOfficers(userToUpdate.getId(), userToUpdate.getBranch(), userToUpdate.getDistrict());
            return mohRepo.save(userToUpdate);
        }

    @Override
    public List<PHIOfficer> getAppUserById(Long id) {
        MOHOfficer mohOfficer = mohRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register the user."));
        return mohOfficer.getPhiOfficers();
    }

    @Override
    public ResponseDiseaseNotificationDto getDiseaseNotificationByPateintId(String id) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Patient not found. Please add this patient's details first."));
        ResponsePatientDto patientDto = modelMapper.map(patient, ResponsePatientDto.class);
        CommunicableDiseaseNotification diseaseNotification = communicableDiseaseNotificationRepo.findByPatient_Nic(id);
        if (diseaseNotification == null) {
            throw new NoDataFoundException("No disease notification found for this patient.");
        }
        ResponseDiseaseNotificationDto dto = modelMapper.map(diseaseNotification, ResponseDiseaseNotificationDto.class);
        dto.setPatient(patientDto);
        return dto;

    }

    @Override
    public ResponseDiseaseNotificationDto saveDiseaseNotification(RequestDiseaseNotificationDto notification) {
        Optional<Patient> optionalPatient = patientRepo.findById(notification.getPatient().getNic());
        if (optionalPatient.isPresent()) {
            throw new UserAlreadyExistsException("Patient already exists. Please update the disease notification according to pateint nic.");
        }
        Patient newPatient = modelMapper.map(notification.getPatient(), Patient.class);
        newPatient = patientRepo.save(newPatient);
        CommunicableDiseaseNotification newNotify = modelMapper.map(notification, CommunicableDiseaseNotification.class);
        newNotify.setPatient(newPatient); // ✅ Assign saved patient
        newNotify = communicableDiseaseNotificationRepo.save(newNotify);
        return modelMapper.map(newNotify, ResponseDiseaseNotificationDto.class);
    }

    @Override
    public List<ResponseDiseaseNotificationDto> getAllNotifications() {
        List<CommunicableDiseaseNotification> notifyList = communicableDiseaseNotificationRepo.findAll();
        if (notifyList.isEmpty()) {
            throw new NoDataFoundException("No disease notifications found");
        }

        List<ResponseDiseaseNotificationDto> responseList = new ArrayList<>();
        for (CommunicableDiseaseNotification notification : notifyList) {
            responseList.add(modelMapper.map(notification, ResponseDiseaseNotificationDto.class));
        }
        return responseList;
    }

    @Override
    public ResponseDiseaseNotificationDto updateDiseaseNotificationByNic(String nic, Map<String, Object> updates) {
        // Fetch the disease notification by patient NIC
        CommunicableDiseaseNotification existingNotification = communicableDiseaseNotificationRepo.findByPatient_Nic(nic);
        if (existingNotification == null) {
            throw new NoDataFoundException("Disease notification not found for patient with NIC: " + nic);
        }

        // Get the notification object to update
        CommunicableDiseaseNotification notificationToUpdate = existingNotification;

        // Update fields in the notification
        updates.forEach((key, value) -> {
            switch (key) {
                case "guardianName":
                    notificationToUpdate.setGuardianName((String) value);
                    break;
                case "name":
                    notificationToUpdate.setName((String) value);
                    break;
                case "labResults":
                    notificationToUpdate.setLabResults((String) value);
                    break;
                case "dateOfOnset":
                    notificationToUpdate.setDateOfOnset(LocalDate.parse((String) value));
                    break;
                case "dateOfAdmission":
                    notificationToUpdate.setDateOfAdmission(LocalDate.parse((String) value));
                    break;
                case "institute":
                    notificationToUpdate.setInstitute((String) value);
                    break;
                case "ward":
                    notificationToUpdate.setWard((String) value);
                    break;
                case "bedNumber":
                    notificationToUpdate.setBedNumber((String) value);
                    break;
                case "medicalOfficer":
                    notificationToUpdate.setMedicalOfficer((String) value);
                    break;
                default:
                    throw new InvalidArgumentExeception("Invalid field name: " + key);
            }
        });

        // Save the updated notification
        CommunicableDiseaseNotification updatedNotification = communicableDiseaseNotificationRepo.save(notificationToUpdate);

        // Map the updated notification to the DTO (ResponseDiseaseNotificationDto)
        ResponseDiseaseNotificationDto responseDto = modelMapper.map(updatedNotification, ResponseDiseaseNotificationDto.class);

        // Optionally, update patient data if required (you can fetch and update patient info as needed)
        ResponsePatientDto patientDto = modelMapper.map(updatedNotification.getPatient(), ResponsePatientDto.class);
        responseDto.setPatient(patientDto);

        return responseDto;
    }

    @Override
    public ResponseDiseaseNotificationDto deleteDiseaseNotificationById(String id) {
        // Find existing notification
        CommunicableDiseaseNotification existingNotification = communicableDiseaseNotificationRepo.findByPatient_Nic(id);
        if (existingNotification == null) {
            throw new NoDataFoundException("diesease notification is not found. Please register the user.");
        }
        if (patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
        }

        communicableDiseaseNotificationRepo.deleteById(existingNotification.getId());
        return modelMapper.map(existingNotification, ResponseDiseaseNotificationDto.class);
    }

    @Override
    public InwardDocument saveInwardDocument(RequestInwardDocumentDto document) {
        if(inwardDocumentRepo.existsByOriginalNumberAndInwardNumber(document.getOriginalNumber(),document.getInwardNumber())){
            throw new DataAlreadyExistsException("Inward document is already exists");
        }
        InwardDocument newDocument = modelMapper.map(document,InwardDocument.class);
        return inwardDocumentRepo.save(newDocument);

    }

    @Override
    public List<InwardDocument> getAllInwardDocument() {
        List<InwardDocument> documentList = inwardDocumentRepo.findAll();
        if(documentList.isEmpty()){
            throw  new NoDataFoundException("no users are added to Appusers ");
        }
        return documentList;
    }

    @Override
    public InwardDocument getInwardDocumentById(Long id) {
        Optional<InwardDocument> document = inwardDocumentRepo.findById(id);
        if (document.isEmpty()) {
            throw new NoDataFoundException("user is not found. please register the user");
        }

        return document.get();
    }

    @Override
    public InwardDocument updateInwardDocument(Long id, Map<String, Object> updates) {
        return null;
    }

    @Override
    public InwardDocument deleteInwardDocument(Long id) {
        Optional<InwardDocument> existingDocument = inwardDocumentRepo.findById(id);
        if (existingDocument.isEmpty()) {
            throw new NoDataFoundException("Inward document is not found");
        }
        inwardDocumentRepo.deleteById(id);
        return existingDocument.get();
    }


}








