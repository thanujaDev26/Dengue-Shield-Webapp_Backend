package com.dengue_webapp.dengue_webapp.controller;


import com.dengue_webapp.dengue_webapp.model.entity.InfectiousCaseReport;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.service.InfectiousCaseReportService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/report")
public class InfectiousCaseReportController {


    @Autowired
    private InfectiousCaseReportService infectiousCaseReportService;

     @GetMapping("/getAllReports")
     public ResponseEntity<StandardResponse> getAllReports() {

         List<InfectiousCaseReport> reportList =  infectiousCaseReportService.getAllReports();
         StandardResponse response = new StandardResponse(200, "get all reports successfully", reportList );
         return ResponseEntity.ok(response);
     }



}
