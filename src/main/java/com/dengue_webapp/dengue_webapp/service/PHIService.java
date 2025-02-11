package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;

import java.util.List;

public interface PHIService {

    public PHIOfficer createPHI(RequestPHIDto dto);
   public void  assignMOHOfficer(PHIOfficer newPhiOfficer, String branch, String district);
    public ResponsePHIDto getPHI(long id);

    public void deletePHI(long id);

    public void updatePHI(long id, RequestPHIDto dto);

    public List<ResponsePHIDto> getAllPHI(String searchText, int page, int size);

}