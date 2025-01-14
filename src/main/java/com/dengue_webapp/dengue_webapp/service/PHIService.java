package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;

import java.util.List;

public interface PHIService {

    public void createPHI(RequestPHIDto dto);

    public ResponsePHIDto getPHI(String id);

    public void deletePHI(String id);

    public void updatePHI(String id, RequestPHIDto dto);

    public List<ResponsePHIDto> getAllPHI(String searchText, int page, int size);

}