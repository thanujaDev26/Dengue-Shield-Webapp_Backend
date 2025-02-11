package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestMOHDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponseMOHDto;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.service.MOHService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/moh")
public class MOHController {

    @Autowired
    private MOHService mohService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register-moh")
    public ResponseEntity<StandardResponse> registerMOHOfficer(@RequestBody RequestMOHDto mohOfficerDetails) {
        try {
            MOHOfficer newMohOfficer = mohService.registerMOHOfficer(mohOfficerDetails);

            // Convert MOHOfficer to ResponseMOHDto using ModelMapper or manually
            ResponseMOHDto responseDto = modelMapper.map(newMohOfficer, ResponseMOHDto.class);

            return new ResponseEntity<>(
                    new StandardResponse(201, "Successfully registered a MoH Officer", responseDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


//crud want


