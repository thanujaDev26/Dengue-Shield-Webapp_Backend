package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.PHIOfficer;
import com.dengue_webapp.dengue_webapp.repository.PHIRepo;
import com.dengue_webapp.dengue_webapp.service.PHIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PHIServiceImpl implements PHIService {

    private final PHIRepo phiRepo;

    @Autowired
    public PHIServiceImpl(PHIRepo phiRepo) {
        this.phiRepo = phiRepo;
    }

    @Override
    public void createPHI(RequestPHIDto dto){
        PHIOfficer phiOfficer = new PHIOfficer(
                dto.getId(), dto.getName(), dto.getPhone(), dto.getEmail(), dto.getArea(), dto.getAddress()
        );

        phiRepo.save(phiOfficer);
    }

    @Override
    public ResponsePHIDto getPHI(String id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();
        return new ResponsePHIDto(
                phi.getId(), phi.getName(), phi.getPhone(), phi.getEmail(), phi.getArea(), phi.getAddress()
        );
    }

    @Override
    public void deletePHI(String id) {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();
        phiRepo.deleteById(phi.getId());
    }

    @Override
    public void updatePHI(String id, RequestPHIDto dto)  {
        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);

        if(phiOfficer.isEmpty()){
            throw new RuntimeException("PHI Not Found");
        }
        PHIOfficer phi = phiOfficer.get();

        phi.setName(dto.getName());
        phi.setPhone(dto.getPhone());
        phi.setEmail(dto.getEmail());
        phi.setArea(dto.getArea());
        phi.setAddress(dto.getAddress());

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
            dtos.add(new ResponsePHIDto(
                    phi.getId(), phi.getName(), phi.getPhone(), phi.getEmail(), phi.getArea(), phi.getAddress()
            ));
        });

        return dtos;
    }


}