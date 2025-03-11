package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPatientDto;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.Patient;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.PatientRepo;
import com.dengue_webapp.dengue_webapp.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class patientServiceIMPL implements PatientService {
    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Patient registerPatient(RequestPatientDto user) {
        if (patientRepo.existsById(user.getId())) {
            throw new UserAlreadyExistsException("Patient " + user.getName() + " is already registered.");
        }
        // Convert DTO to Entity
        Patient newPatient = modelMapper.map(user,Patient.class);
        // Save to database
        return patientRepo.save(newPatient);
    }

    @Override
    public List<Patient> getAllpatients() {
        List<Patient> userList = patientRepo.findAll();
        if(userList.isEmpty()){
            throw  new NoDataFoundException("no patients are added  ");
        }
        return userList;
    }

    @Override
    public Patient getPatientById(String id) {
        Optional<Patient> existingPatient = patientRepo.findById(id);
        if (existingPatient.isEmpty()) {
            throw new NoDataFoundException("Patient is not found. please register ");
        }

        return existingPatient.get();
    }

    @Override
    public Patient updatePatient(String id, Map<String, Object> updates) {
        Optional<Patient> existingPatient = patientRepo.findById(id);
        if (existingPatient.isEmpty()) {
            throw new NoDataFoundException("Patient is not found...please register the patient");
        }

        Patient patientToUpdate = existingPatient.get();
        updates.forEach((key, value) -> {
            switch (key) {
                case "id":
                    patientToUpdate.setId((String) value);
                    break;
                case "name":
                    patientToUpdate.setName((String) value);
                    break;
                case "address":
                    patientToUpdate.setAddress((String) value);
                    break;
                case "race":
                    patientToUpdate.setRace((String) value);
                    break;
                case "religion":
                    patientToUpdate.setReligion((String) value);
                    break;
                case "telephoneNumber":
                    patientToUpdate.setTelephoneNumber((String) value);
                    break;
                case "age":
                    // Assuming you want to parse a LocalDate from a string
                    patientToUpdate.setAge((Integer) value);
                    break;
                case "guardianName":
                    patientToUpdate.setGuardianName((String) value);
                    break;
                case "occupation":
                    patientToUpdate.setOccupation((String) value);
                    break;
                case "gender":
                    patientToUpdate.setGender((String) value);
                    break;
                default:
                    throw new InvalidArgumentExeception("Invalid field name: " + key);
            }
        });

        Patient updatedPatient = patientRepo.save(patientToUpdate);
        return updatedPatient;
    }

    @Override
    public Patient deletePatient(String id) {
        Optional<Patient> existingUser = patientRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("patient is not found. please register the patient");
        }
        patientRepo.deleteById(id);
        return existingUser.get();
    }

}

