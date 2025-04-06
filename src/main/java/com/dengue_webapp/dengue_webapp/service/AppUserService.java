package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestLoginDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;

import java.util.List;
import java.util.Map;

public interface AppUserService {
    AppUser registerUser(RequestAppUserDto user);

    List<AppUser> getAllAppUsers();



    String deleteAppUser(Long id,String role);

    Object updateAppUser(Long id,String role, Map<String ,Object> Updates);

    Object loginAppUser(RequestLoginDto user);

    Object getAppUserByIdAndRole(Long id, String role);
}
