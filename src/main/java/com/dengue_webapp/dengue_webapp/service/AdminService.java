package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAdminDto;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;

import java.util.List;

public interface AdminService {
    AppUser registerAdmin(RequestAdminDto user);

    List<AppUser> getAllAdminUsers();

    AppUser getAdminUserById(Long id);

    AppUser deleteAdminUser(Long id);

    AppUser updateAdminUser(Long id, RequestAdminDto updatedUser);
}
