package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPreApprovedUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface PreApprovedUserService {
    PreApprovedUser addPreApprovedUser(RequestPreApprovedUserDto user);

    List<PreApprovedUser> getAllPreApprovedUsers();

    PreApprovedUser getPreApprovedUserById(Long id);

    PreApprovedUser deletePreApprovedUser(Long id);

    PreApprovedUser updatePreApprovedUser(Long id, @RequestBody Map<String ,Object> Updates);
}
