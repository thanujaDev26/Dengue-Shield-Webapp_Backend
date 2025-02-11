package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestMOHDto;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;

public interface MOHService {
    MOHOfficer registerMOHOfficer(RequestMOHDto mohOfficerDetails);
    void assignPHIOfficers(MOHOfficer mohOfficer,String branch,String district);
}
