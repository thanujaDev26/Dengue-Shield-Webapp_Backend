package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phi")
public class PHIController {

    @Autowired
    private  PHIService phiService;

    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/register-phi")
    public ResponseEntity<StandardResponse> createPHIOfficerInfo(@RequestBody RequestPHIDto dto) {
        PHIOfficer createdPHI = phiService.createPHI(dto);

        if (createdPHI == null) {
            return new ResponseEntity<>(
                    new StandardResponse(400, "Failed to create PHI Officer. Email might already exist or MOH Officer not found.", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        ResponsePHIDto response = modelMapper.map(createdPHI, ResponsePHIDto.class);

        return new ResponseEntity<>(
                new StandardResponse(201, "Successfully created a PHI Officer", response),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public String getPHIOfficerInfo(){
        return "PHI Officer Info";
    }


    @GetMapping("/{id}")
    public String getPHIOfficerInfo(@PathVariable String id){
        return "PHI Officer Info for " + id;
    }


    @GetMapping(path = "/list", params = {"searchText", "page", "size"})
    public String getAllPHIOfficerInfo(@RequestParam String searchText, @RequestParam int page, @RequestParam int size){
        return "All PHI Officer Info List for " + searchText + "" + page + "" + size + "";
    }

    @PutMapping( "/{id}")
    public String updatePHIOfficerInfo(@PathVariable String id){
        return "Update PHI Officer Info for " + id;
    }

    @DeleteMapping(path = "/{id}")
    public String deletePHIOfficerInfo(@PathVariable String id){
        return "Delete PHI Officer Info for " + id;
    }


}
