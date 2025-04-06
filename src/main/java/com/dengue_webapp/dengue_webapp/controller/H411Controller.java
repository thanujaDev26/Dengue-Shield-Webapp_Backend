package com.dengue_webapp.dengue_webapp.controller;

import com.dengue_webapp.dengue_webapp.dto.request.RequestH411Dto;
import com.dengue_webapp.dengue_webapp.service.H411Service;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/H411")
public class H411Controller {

    @Autowired
    private H411Service h411Service;


    @PostMapping("/saveExtendedFormData/{messageId}")
    public ResponseEntity<StandardResponse> saveExtendedFormData(
            @PathVariable Long messageId,
            @RequestBody RequestH411Dto h411Dto) {
        System.out.println("hello in controller");
       String msg = h411Service.saveExtendedFormData(messageId,h411Dto);
        StandardResponse response = new StandardResponse(201, "successfully saved the h411",  msg);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
/*     @PostMapping("/login")
    public ResponseEntity<StandardResponse> loginAppUser(@RequestBody RequestLoginDto user) {

        Object loggedUser = appUserService.loginAppUser(user);
        StandardResponse response = new StandardResponse(201, " user added successfully", loggedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }*/