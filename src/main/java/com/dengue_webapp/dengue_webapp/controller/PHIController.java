package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/phi")
public class PHIController {

    private final PHIService phiService;

    public PHIController(PHIService phiService) {
        this.phiService = phiService;
    }


    @PostMapping
    public ResponseEntity<StandardResponse> createPHIOfficerInfo(@RequestBody RequestPHIDto dto){
       phiService.createPHI(dto);

       return new ResponseEntity<>(
               new StandardResponse(201, "Successfully created a PHI Officer", dto),
               HttpStatus.CREATED
       );
    }

//    @PostMapping
//    public String createPHIOfficerInfo(){
//        return "Create PHI Officer Info";
//    }

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
