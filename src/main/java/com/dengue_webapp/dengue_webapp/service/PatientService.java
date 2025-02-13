package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPatientDto;
import com.dengue_webapp.dengue_webapp.model.entity.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService {
    Patient registerAppUser(RequestPatientDto user);

    List<Patient> getAllpatients();

    Patient getPatientById(String id);

    Patient updatePatient(String id, Map<String, Object> updates);

    Patient deletePatient(String id);
}
