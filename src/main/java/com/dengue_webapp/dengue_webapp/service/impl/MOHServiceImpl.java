package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.*;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseDiseaseNotificationDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePatientDto;
import com.dengue_webapp.dengue_webapp.exceptions.DataAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.*;
import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.*;
import com.dengue_webapp.dengue_webapp.service.MOHService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.swing.text.html.Option;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public List<PHIOfficer> assignPHIOfficers( RequestMOhInfo mohinfo) {
        // Fetch MOHOfficer and handle if not found
        System.out.println(mohinfo);
        MOHOfficer mohOfficer = mohRepo.findById(mohinfo.getId())
                .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register or provide a correct ID."));
        List<PHIOfficer> phiOfficers = phiRepo.findAllByDistrictAndAndBranch(mohinfo.getDistrict(), mohinfo.getBranch());
        System.out.println(phiOfficers);
        if (phiOfficers.isEmpty()) {
            throw new NoDataFoundException("No PHI Officers found in the given district and branch. Please register PHI Officers or add district and branch");
        }
        phiOfficers.forEach((phi) -> phi.setMohOfficer(mohOfficer));
        return phiOfficers;
    }


    @Override
    public MOHOfficer updateMohOfficer(Long id, Map<String, Object> updates) {

            // Fetch MOHOfficer, throw an exception if not found
            MOHOfficer userToUpdate = mohRepo.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register the user."));
        System.out.println(userToUpdate);
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
                    case "branch":
                        userToUpdate.setBranch(((String) value).toLowerCase());
                        break;
                    default:
                        throw new InvalidArgumentExeception("Invalid field name: " + key);
                }
            });
            //`assignPHIOfficers(userToUpdate.getId(), userToUpdate.getBranch(), userToUpdate.getDistrict());
            return mohRepo.save(userToUpdate);
        }

    @Override
    public List<PHIOfficer> getAppUserById(Long id) {
        MOHOfficer mohOfficer = mohRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("MOH Officer not found. Please register the user."));
        List<PHIOfficer> phiOfficers = phiRepo.findAllByMohOfficer(mohOfficer);
        return phiOfficers;
    }

    @Override
    public ResponseDiseaseNotificationDto getDiseaseNotificationByPateintId(String id) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Patient not found. Please add this patient's details first."));
        ResponsePatientDto patientDto = modelMapper.map(patient, ResponsePatientDto.class);
        CommunicableDiseaseNotification diseaseNotification = communicableDiseaseNotificationRepo.findByPatient_Id(id);
        if (diseaseNotification == null) {
            throw new NoDataFoundException("No disease notification found for this patient.");
        }
        ResponseDiseaseNotificationDto dto = modelMapper.map(diseaseNotification, ResponseDiseaseNotificationDto.class);
        dto.setPatient(patientDto);
        return dto;

    }
    @Transactional
    @Override
    public CommunicableDiseaseNotification saveDiseaseNotification(RequestDiseaseNotificationDto notification) {
        System.out.println("in service impl");

        // Fetching Patient
        Optional<Patient> optionalPatient = patientRepo.findById(notification.getPatient().getId());
        if (optionalPatient.isEmpty()) {
            String errorMsg = "Patient with ID " + notification.getPatient().getId() + " is not registered in the system.";
            System.out.println(errorMsg);
            throw new NoDataFoundException(errorMsg);
        }
        Patient patient = optionalPatient.get();
        System.out.println("Fetched patient: " + patient.getName());

        // Fetching MOH Officer
        Optional<MOHOfficer> optionalMOHOfficer = mohRepo.findById(notification.getMohOfficerId());
        if (optionalMOHOfficer.isEmpty()) {
            String errorMsg = "MOH Officer with ID " + notification.getMohOfficerId() + " is not registered in the system.";
            System.out.println(errorMsg);
            throw new NoDataFoundException(errorMsg);
        }
        MOHOfficer mohOfficer = optionalMOHOfficer.get();
        System.out.println("Fetched MOH Officer: " + mohOfficer.getAppuser().getName());

        // Mapping the notification
       // CommunicableDiseaseNotification newNotify = modelMapper.map(notification, CommunicableDiseaseNotification.class);
        CommunicableDiseaseNotification newNotify = new CommunicableDiseaseNotification(notification.getLabResults(),
                notification.getDateOfOnset(),
                notification.getDateOfAdmission(),
                notification.getInstitute(),
                notification.getWard(),
                notification.getBedNumber(),
                notification.getNameOfNotifier(),
                notification.getNotifierStatus(),
                notification.getDiseaseName(),
                mohOfficer,patient);

        System.out.println(newNotify);


        System.out.println("Notification created for patient " + patient.getName() + " by MOH Officer " + mohOfficer.getAppuser().getName());


        System.out.println(newNotify);

        communicableDiseaseNotificationRepo.save(newNotify);

        return newNotify;
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
    public ResponseDiseaseNotificationDto updateDiseaseNotificationById(long id, Map<String, Object> updates) {
        System.out.println("check 1");

        Optional<CommunicableDiseaseNotification> existingNotification = communicableDiseaseNotificationRepo.findById(id);
        if (existingNotification.isEmpty()) {
            throw new NoDataFoundException("Disease notification not found for this id " + id);
        }

        System.out.println("check 2");
        System.out.println(existingNotification.get());
        System.out.println(updates);

        CommunicableDiseaseNotification notification = existingNotification.get();
        System.out.println("check 2.5");
        // Update fields in the notification
        updates.forEach((key, value) -> {
            if (value == null) return; // Skip null values

            switch (key) {
                case "nameOfNotifier":
                    notification.setNameOfNotifier((String) value);
                    System.out.println("notifier name printed"+notification.getNameOfNotifier());
                    break;
                case "labResults":
                    notification.setLabResults((String) value);
                    System.out.println("lab results printed" +notification.getLabResults());
                    break;
                case "dateOfOnset":
                    try {
                        notification.setDateOfOnset(LocalDate.parse((String) value));
                        System.out.println("date onset is printed"+notification.getDateOfOnset());
                    } catch (Exception e) {
                        throw new InvalidArgumentExeception("Invalid date format for dateOfOnset");
                    }
                    break;
                case "dateOfAdmission":
                    try {
                        notification.setDateOfAdmission(LocalDate.parse((String) value));
                        System.out.println("date of admission is printed"+notification.getDateOfAdmission());
                    } catch (Exception e) {
                        throw new InvalidArgumentExeception("Invalid date format for dateOfAdmission");
                    }
                    break;
                case "institute":
                    notification.setInstitute((String) value);
                    System.out.println("institute is printed"+notification.getInstitute());
                    break;
                case "ward":
                    notification.setWard((String) value);
                    System.out.println("ward is printed"+notification.getWard());
                    break;
                case "bedNumber":
                    notification.setBedNumber((String) value);
                    System.out.println("bedNumber is printed"+notification.getBedNumber());
                    break;
                case "notifierStatus":
                    notification.setNotifierStatus((String) value);
                    System.out.println("Notifier Status is printed"+notification.getNotifierStatus());
                    break;
                case "diseaseName":
                    notification.setDiseaseName((String) value);
                    System.out.println("diseaseName is printed"+notification.getDiseaseName());
                    break;
                default:
                    throw new InvalidArgumentExeception("Invalid field name: " + key);
            }
        });

        System.out.println("check 3");

        // Save the updated notification
        CommunicableDiseaseNotification updatedNotification = communicableDiseaseNotificationRepo.save(notification);
        System.out.println("check 4");

        // Map the updated notification to the DTO
        ResponseDiseaseNotificationDto responseDto = modelMapper.map(updatedNotification, ResponseDiseaseNotificationDto.class);
        System.out.println("check 5");

        // Optionally, update patient data if required
        ResponsePatientDto patientDto = modelMapper.map(updatedNotification.getPatient(), ResponsePatientDto.class);
        responseDto.setPatient(patientDto);

        return responseDto;
    }


    @Override
    public ResponseDiseaseNotificationDto deleteDiseaseNotificationById(String id) {
        // Find existing notification
        CommunicableDiseaseNotification existingNotification = communicableDiseaseNotificationRepo.findByPatient_Id(id);
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
    public Message sendDiseaseNotification(RequestMessageDto message) {
        // Find the MOHOfficer by ID
        Optional<MOHOfficer> mohOfficerOpt = mohRepo.findById(message.getMohOfficerId());
        if (mohOfficerOpt.isEmpty()) {
            throw new NoDataFoundException("MOH Officer not found.");
        }

        Optional<PHIOfficer> phiOfficerOpt = phiRepo.findById(message.getPhiOfficerId());
        if (phiOfficerOpt.isEmpty()) {
            throw new NoDataFoundException("Phi Officer not found.");
        }

        Optional<CommunicableDiseaseNotification> h544Opt = communicableDiseaseNotificationRepo.findById(message.getH544Id());
        if (h544Opt.isEmpty()) {
            throw new NoDataFoundException("h544 not found.");
        }
        // Create a new message
        Message newMessage = Message.builder()
                .mohOfficer(mohOfficerOpt.get()) // Set MOH Officer
                .phiOfficer(phiOfficerOpt.get()) // Set PHI Officer
                .h544(h544Opt.get()) // Set linked Inward Document
                .status(MessageStatus.PENDING) // Default status
                .createdAt(Date.from(Instant.now())) // Current timestamp
                .updatedAt(Date.from(Instant.now())) // Current timestamp
                .build();
//
        return messageRepo.save(newMessage);
    }

    @Override
    public List<Message> getAllMessagesById(long id) {
        Optional<MOHOfficer> mohOfficerOpt = mohRepo.findById(id);
        if (mohOfficerOpt.isEmpty()) {
            throw new NoDataFoundException("MOH Officer is not found.");
        }
        List<Message> messageList = messageRepo.findAllByMohOfficer(mohOfficerOpt.get());
        if(messageList.isEmpty()){
            throw new NoDataFoundException("No messages are found");
        }
        return messageList;
    }

    @Override
    public String deleteMessageById(long id) {
        Message message = messageRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Message is not found"));
        System.out.println(message);
        CommunicableDiseaseNotification h544 = message.getH544();

        if (h544 == null) {
            throw new NoDataFoundException("h544 is not found");
        }
        System.out.println(message);
        try {
            messageRepo.delete(message);
            communicableDiseaseNotificationRepo.delete(h544);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "Message and associated h544 deleted successfully";
    }

    @Override
    public List<PHIOfficer> getAllPhisId(long id) {
        Optional<MOHOfficer> optionalMOHOfficer = mohRepo.findById(id);
        if(optionalMOHOfficer.isEmpty()){
            throw new NoDataFoundException("Moh officer is not found");
        }
        List<PHIOfficer> assighnedPhiList = phiRepo.findAllByDistrictAndAndBranch(optionalMOHOfficer.get().getDistrict(),optionalMOHOfficer.get().getBranch());
        return assighnedPhiList;
    }

    @Override
    public String updatePhi(long mohId, long phiId) {
        Optional<MOHOfficer> mohOfficer = mohRepo.findById(mohId);
        if(mohOfficer.isEmpty()){
            throw new NoDataFoundException("Mohofficer is not found");
        }
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(phiId);
        if(phiOfficer.isEmpty()){
            throw new NoDataFoundException("PhiOfficer is not found");
        }

        phiOfficer.get().setMohOfficer(mohOfficer.get());
        phiRepo.save(phiOfficer.get());
        return "you have successfully assign phi officer";
    }

    @Override
    public String unassignPhi(long id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new NoDataFoundException("PhiOfficer is not found");
        }
        phiOfficer.get().setMohOfficer(null);
        phiRepo.save(phiOfficer.get());
        return "phi officer successfully unassigned";
    }


}








