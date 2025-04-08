package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.model.entity.InfectiousCaseReport;
import com.dengue_webapp.dengue_webapp.repository.InfectiousCaseRepo;
import com.dengue_webapp.dengue_webapp.service.InfectiousCaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfectiousCaseReportServiceImpl implements  InfectiousCaseReportService {

    @Autowired
    private InfectiousCaseRepo infectiousCaseRepo;
    @Override
    public List<InfectiousCaseReport> getAllReports() {
        List<InfectiousCaseReport> reports = infectiousCaseRepo.findAll();
        return reports;
    }
}
