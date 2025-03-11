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
        List<PHIOfficer> list = mohOfficer.getPhiOfficers();
        phiOfficers.forEach((phi) -> list.add(phi));
        mohOfficer.setPhiOfficers(list);
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
        return mohOfficer.getPhiOfficers();
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


        //newNotify.setPatient(patient); // Setting the patient

        // Add the notification to the MOH Officer
        System.out.println(newNotify);


        System.out.println("Notification created for patient " + patient.getName() + " by MOH Officer " + mohOfficer.getAppuser().getName());

// Add the notification to the MOH Officer's list
//        mohOfficer.getNotifications().add(newNotify);
        System.out.println(newNotify);
// Save the notification to the repository
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
    public ResponseDiseaseNotificationDto updateDiseaseNotificationByNic(String nic, Map<String, Object> updates) {
        // Fetch the disease notification by patient NIC
        CommunicableDiseaseNotification existingNotification = communicableDiseaseNotificationRepo.findByPatient_Id(nic);
        if (existingNotification == null) {
            throw new NoDataFoundException("Disease notification not found for patient with NIC: " + nic);
        }

        // Get the notification object to update
        CommunicableDiseaseNotification notificationToUpdate = existingNotification;

        // Update fields in the notification
        updates.forEach((key, value) -> {
            switch (key) {
                case "nameOfNotifier":
                    notificationToUpdate.setNameOfNotifier((String) value);
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
                case "notifierStatus":
                    notificationToUpdate.setNotifierStatus((String) value);
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
        return null;
    }

//    @Override
//    public Message sendDiseaseNotification(RequestMessageDto messageDto) {
//        // Find the MOHOfficer by ID
//        Optional<MOHOfficer> mohOfficerOpt = mohRepo.findById(messageDto.getMohOfficerId());
//        if (mohOfficerOpt.isEmpty()) {
//            throw new NoDataFoundException("MOH Officer not found.");
//        }
//
//        // Find the PHIOfficer by email
//        PHIOfficer phiOfficerOpt = phiRepo.findByAppuser_Email(messageDto.getPhiOfficerEmail());
//        if (phiOfficerOpt == null) {
//            throw new NoDataFoundException("PHI Officer not found.");
//        }
//
//        // Get MOH Officer's notifications list
//        List<CommunicableDiseaseNotification> notifications = mohOfficerOpt.get().getNotifications();
//        if (notifications == null || notifications.isEmpty()) {
//            throw new NoDataFoundException("No disease notifications found for this MOH Officer.");
//        }
//
//        // Get the last notification
//        CommunicableDiseaseNotification h544 = notifications.get(notifications.size() - 1);
//
//        // Create a new message
//        Message message = Message.builder()
//                .mohOfficer(mohOfficerOpt.get()) // Set MOH Officer
//                .phiOfficer(phiOfficerOpt) // Set PHI Officer
//                .h544(h544) // Set linked Inward Document
//                .status(MessageStatus.PENDING) // Default status
//                .createdAt(Date.from(Instant.now())) // Current timestamp
//                .updatedAt(Date.from(Instant.now())) // Current timestamp
//                .build();
//
//        // Save the message entity to the database
//        return messageRepo.save(message);
//    }


}








