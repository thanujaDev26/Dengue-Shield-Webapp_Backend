package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.service.AppUserService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vi/admin")
public class AppUserController {
   @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerAppUser(@RequestBody RequestAppUserDto user) {

        AppUser savedUser = appUserService.registerAppUser(user);
        StandardResponse response = new StandardResponse(201, "Pre-approved user added successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/getAllAppUsers")
    public ResponseEntity<StandardResponse> getAllAppUsers() {

        List<AppUser> userList = appUserService.getAllAppUsers();
        StandardResponse response = new StandardResponse(200, "Fetched all pre-approved users", userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppUser/{id}")
    public ResponseEntity<StandardResponse> getAppUserById(@PathVariable Long id) {
        AppUser savedUser = appUserService.getAppUserById(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user fetched successfully", savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @DeleteMapping("/deleteAppUser/{id}")
    public ResponseEntity<StandardResponse> deleteAppUser(@PathVariable Long id) {
        AppUser DeletedUser = appUserService.deleteAppUser(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateAppUser/{id}")
    public ResponseEntity<StandardResponse> updateAppUser(@PathVariable Long id, @RequestBody RequestAppUserDto updatedUser) {
        AppUser UpdatedUser = appUserService.updateAppUser(id, updatedUser);
        StandardResponse response = new StandardResponse(200, "Pre-approved user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

