package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestLoginDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;

import java.util.List;
import java.util.Map;

public interface AppUserService {
    AppUser registerUser(RequestUserDto user);

    List<AppUser> getAllAppUsers();

    AppUser getAppUserById(Long id);

    AppUser deleteAppUser(Long id);

    AppUser updateAppUser(Long id, Map<String ,Object> Updates);

    Object loginAppUser(RequestLoginDto user);
}
