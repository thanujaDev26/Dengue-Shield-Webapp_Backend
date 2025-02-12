package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAdminDto;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.AppUserRepo;
import com.dengue_webapp.dengue_webapp.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dengue_webapp.dengue_webapp.model.enums.Role.ROLE_ADMIN;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public AppUser registerAdmin(RequestAdminDto user) {

        if (appUserRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User is already registered. Please log in.");
        }

        if ("ROLE_ADMIN".equals(user.getRole().name())) { // Assuming role is an Enum
            AppUser newAdmin = modelMapper.map(user, AppUser.class);
            return appUserRepo.save(newAdmin); // Return the saved entity
        }

        throw new InvalidArgumentExeception("Invalid role. Only ROLE_ADMIN is allowed.");


    }

    @Override
    public List<AppUser> getAllAdminUsers() {
        Role roleName = ROLE_ADMIN;
        List<AppUser> userList = appUserRepo.findAllByRole(roleName);
        if(userList.isEmpty()){
            throw  new NoDataFoundException("no Admins are added to Appusers ");
        }
        return userList;
    }

    @Override
    public AppUser getAdminUserById(Long id) {
        return appUserRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("AdminUser iss not found . please register the user"));
    }

    @Override
    public AppUser deleteAdminUser(Long id) {
        Optional<AppUser> existingUser = appUserRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a Admin user. please register the user");
        }
        appUserRepo.deleteById(id);
        return existingUser.get();
    }

    @Override
    public AppUser updateAdminUser(Long id, RequestAdminDto updatedUser) {
        Optional<AppUser> existingUser = appUserRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a Admin user. please register the user");
        }
        AppUser userToUpdate = existingUser.get();
        modelMapper.map(updatedUser, userToUpdate);  // Update fields of the existing user
        AppUser updatedUserResponse = appUserRepo.save(userToUpdate);
        return updatedUserResponse;
    }

}
