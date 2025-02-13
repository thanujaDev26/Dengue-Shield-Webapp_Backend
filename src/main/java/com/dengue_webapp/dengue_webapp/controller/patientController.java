package com.dengue_webapp.dengue_webapp.controller;

//this can be accessed only by MOH officer

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestPatientDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.Patient;
import com.dengue_webapp.dengue_webapp.service.PatientService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patient")
public class patientController {

    @Autowired
    private PatientService  patientService;
    //crud
    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerAppUser(@RequestBody RequestPatientDto user) {

        Patient savedUser =  patientService.registerAppUser(user);
        StandardResponse response = new StandardResponse(201, " user added successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/getAllAppUsers")
    public ResponseEntity<StandardResponse> getAllpatients() {

        List<Patient> patientList =  patientService.getAllpatients();
        StandardResponse response = new StandardResponse(200, "Fetched all  users",patientList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPatient/{id}")
    public ResponseEntity<StandardResponse> getPatientById(@PathVariable  String id) {

        Patient savedUser = patientService.getPatientById(id);
        StandardResponse response = new StandardResponse(200, " user fetched successfully", savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @DeleteMapping("/deleteAppUser/{id}")
    public ResponseEntity<StandardResponse> deletePatient(@PathVariable String id) {
        Patient DeletedUser = patientService.deletePatient(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateAppUser/{id}")
    public ResponseEntity<StandardResponse> updatePatient(@PathVariable(value = "id")  String id, @RequestBody Map<String ,Object> Updates) {

        Patient UpdatedPatient = patientService.updatePatient(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",  UpdatedPatient);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    }
