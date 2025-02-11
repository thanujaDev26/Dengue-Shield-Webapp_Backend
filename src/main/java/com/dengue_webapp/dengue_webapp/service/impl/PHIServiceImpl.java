package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.repository.MOHRepo;
import com.dengue_webapp.dengue_webapp.repository.PHIRepo;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PHIServiceImpl implements PHIService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  PHIRepo phiRepo;

    @Autowired
    private MOHRepo mohRepo;
    @Override
    public PHIOfficer createPHI(RequestPHIDto dto){
        try {
            String email = dto.getEmail();

            // Check if email already exists
            if (!phiRepo.existsByEmail(email)) {
                PHIOfficer newPhiOfficer = modelMapper.map(dto, PHIOfficer.class);
                assignMOHOfficer(newPhiOfficer,dto.getDistrict(), dto.getBranch());
                return phiRepo.save(newPhiOfficer); // Save and return the new officer
            } else {
                System.out.println("PHI Officer with this email already exists!");
                return null; // Return null if officer already exists
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Ensure method always returns a value
        }
    }

    public void assignMOHOfficer(  PHIOfficer newPhiOfficer,String branch,String district){
        MOHOfficer mohOfficer = mohRepo.findByDistrictAndBranch(district,branch);
        if(mohOfficer != null){
            newPhiOfficer.setMohOfficer(mohOfficer);
        }

    }


    @Override
    public ResponsePHIDto getPHI(long id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();
        return  modelMapper.map(phi,ResponsePHIDto.class);

    }

    @Override
    public void deletePHI(long id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();

    }

    @Override
    public void updatePHI(long id, RequestPHIDto dto)  {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);

        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();
         phi = modelMapper.map(dto,PHIOfficer.class);
        phiRepo.save(phi);
    }

    @Override
    public List<ResponsePHIDto> getAllPHI(String searchText, int page, int size) {
        if(searchText == null || searchText.isEmpty()){
            searchText = "%%";
        }else{
            searchText = "%" + searchText + "%";
        }

        List<PHIOfficer> phis = phiRepo.searchPHIOfficer(searchText, PageRequest.of(page, size));
        List<ResponsePHIDto> dtos = new ArrayList<>();
        phis.forEach(phi ->{

            dtos.add(modelMapper.map(phi, ResponsePHIDto.class));

        });

        return dtos;
    }


}