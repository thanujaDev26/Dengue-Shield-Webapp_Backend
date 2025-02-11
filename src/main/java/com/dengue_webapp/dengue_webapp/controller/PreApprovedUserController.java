package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestPreApprovedUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import com.dengue_webapp.dengue_webapp.service.PreApprovedUserService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
//user registration for pre approved users and admins
public class PreApprovedUserController {
    @Autowired
    private PreApprovedUserService preApprovedUserService;


    // ✅ Create/Register a pre-approved user
    @PostMapping("/preapproved-users")
    public ResponseEntity<StandardResponse> addPreApprovedUser(@RequestBody RequestPreApprovedUserDto user) {

            PreApprovedUser savedUser = preApprovedUserService.addPreApprovedUser(user);
            StandardResponse response = new StandardResponse(201, "Pre-approved user added successfully", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/preapproved-users")
    public ResponseEntity<StandardResponse> getAllPreApprovedUsers() {

            List<PreApprovedUser> userList = preApprovedUserService.getAllPreApprovedUsers();
            StandardResponse response = new StandardResponse(200, "Fetched all pre-approved users", userList);
            return ResponseEntity.ok(response);
    }

    // ✅ Get a pre-approved user by ID
    @GetMapping("/preapproved-users/{id}")
    public ResponseEntity<StandardResponse> getPreApprovedUserById(@PathVariable Long id) {
        PreApprovedUser savedUser = preApprovedUserService.getPreApprovedUserById(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user fetched successfully", savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }


    // ✅ Update a pre-approved user
    @PatchMapping("/preapproved-users/{id}")
    public ResponseEntity<StandardResponse> updatePreApprovedUser(@PathVariable Long id, @RequestBody RequestPreApprovedUserDto updatedUser) {
           PreApprovedUser UpdatedUser = preApprovedUserService.updatePreApprovedUser(id, updatedUser);
        StandardResponse response = new StandardResponse(200, "Pre-approved user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // ✅ Delete a pre-approved user
    @DeleteMapping("/preapproved-users/{id}")
    public ResponseEntity<StandardResponse> deletePreApprovedUser(@PathVariable Long id) {
        PreApprovedUser DeletedUser = preApprovedUserService.deletePreApprovedUser(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
