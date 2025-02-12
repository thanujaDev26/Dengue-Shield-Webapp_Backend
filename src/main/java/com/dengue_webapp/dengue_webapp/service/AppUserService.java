package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser registerAppUser(RequestAppUserDto user);

    List<AppUser> getAllAppUsers();

    AppUser getAppUserById(Long id);

    AppUser deleteAppUser(Long id);

    AppUser updateAppUser(Long id, RequestAppUserDto updatedUser);
}
