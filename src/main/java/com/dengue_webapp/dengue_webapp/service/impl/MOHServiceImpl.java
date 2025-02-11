package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestMOHDto;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.repository.MOHRepo;
import com.dengue_webapp.dengue_webapp.repository.PHIRepo;
import com.dengue_webapp.dengue_webapp.service.MOHService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MOHServiceImpl implements MOHService {
    @Autowired
    private MOHRepo mohRepo;

    @Autowired
    private PHIRepo phiRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public MOHOfficer registerMOHOfficer(RequestMOHDto mohOfficerDetails) {
        try {
            String email = mohOfficerDetails.getEmail();

            // Check if email already exists
            if (!mohRepo.existsByAppuser_Email(email)) {
                MOHOfficer newMohOfficer = modelMapper.map(mohOfficerDetails, MOHOfficer.class);
                assignPHIOfficers(newMohOfficer,mohOfficerDetails.getDistrict(), mohOfficerDetails.getBranch());
                return mohRepo.save(newMohOfficer); // Save and return the new officer
            } else {
                System.out.println("MOH Officer with this email already exists!");
                return null; // Return null if officer already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Ensure method always returns a value
        }
    }



    @Override
    public void assignPHIOfficers(MOHOfficer mohOfficer,String branch,String district) {
        List<PHIOfficer> phiOfficers = phiRepo.findAllByDistrictAndBranch(district,branch);
        if(!phiOfficers.isEmpty()){
            mohOfficer.setPhiOfficers(phiOfficers);
        }

    }

}
