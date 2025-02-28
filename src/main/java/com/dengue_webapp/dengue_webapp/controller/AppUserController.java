package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestLoginDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.service.AppUserService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/appuser")
public class AppUserController {
   @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse> registerAppUser(@RequestBody RequestUserDto user) {
        System.out.println(user);
        AppUser savedUser = appUserService.registerUser(user);
        StandardResponse response = new StandardResponse(201, " user added successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse> loginAppUser(@RequestBody RequestLoginDto user) {

        Object loggedUser = appUserService.loginAppUser(user);
        StandardResponse response = new StandardResponse(201, " user added successfully", loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/getAllAppUsers")
    public ResponseEntity<StandardResponse> getAllAppUsers() {
        System.out.println("hello");
        List<AppUser> userList = appUserService.getAllAppUsers();
        StandardResponse response = new StandardResponse(200, "Fetched all  users", userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppUser/{id}")
    public ResponseEntity<StandardResponse> getAppUserById(@PathVariable  Long id) {

        AppUser savedUser = appUserService.getAppUserById(id);
        StandardResponse response = new StandardResponse(200, " user fetched successfully", savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);  // Use HttpStatus.OK (200)
    }

    @DeleteMapping("/deleteAppUser/{id}")
    public ResponseEntity<StandardResponse> deleteAppUser(@PathVariable Long id) {
        AppUser DeletedUser = appUserService.deleteAppUser(id);
        StandardResponse response = new StandardResponse(200, " user deleted successfully",DeletedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updateAppUser/{id}")
    public ResponseEntity<StandardResponse> updateAppUser(@PathVariable(value = "id")  Long id, @RequestBody Map<String ,Object> Updates) {
        
        AppUser UpdatedUser = appUserService.updateAppUser(id, Updates);
        StandardResponse response = new StandardResponse(200, "user updated successfully",UpdatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

