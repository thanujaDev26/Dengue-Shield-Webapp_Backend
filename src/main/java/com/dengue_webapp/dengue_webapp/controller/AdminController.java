package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestAdminDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.service.AdminService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vi/admin")
public class AdminController {
   @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerAdmin(@RequestBody RequestAdminDto user) {

        AppUser savedUser = adminService.registerAdmin(user);
        StandardResponse response = new StandardResponse(201, "Pre-approved user added successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/getAllAdminUsers")
    public ResponseEntity<StandardResponse> getAllAdminUsers() {

        List<AppUser> userList = adminService.getAllAdminUsers();
        StandardResponse response = new StandardResponse(200, "Fetched all pre-approved users", userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAdmin/{id}")
    public ResponseEntity<StandardResponse> getAdminUserById(@PathVariable Long id) {
        AppUser savedUser = adminService.getAdminUserById(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user fetched successfully", savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @DeleteMapping("/preapproved-users/{id}")
    public ResponseEntity<StandardResponse> deleteAdminUser(@PathVariable Long id) {
        AppUser DeletedUser = adminService.deleteAdminUser(id);
        StandardResponse response = new StandardResponse(200, "Pre-approved user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/preapproved-users/{id}")
    public ResponseEntity<StandardResponse> updateAdminUser(@PathVariable Long id, @RequestBody RequestAdminDto updatedUser) {
        AppUser UpdatedUser = adminService.updateAdminUser(id, updatedUser);
        StandardResponse response = new StandardResponse(200, "Pre-approved user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

/*






 \


    // ✅ Delete a pre-approved user

 */