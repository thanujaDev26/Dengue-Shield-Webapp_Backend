package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.AppUserRepo;
import com.dengue_webapp.dengue_webapp.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dengue_webapp.dengue_webapp.model.enums.Role.ROLE_ADMIN;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public AppUser registerAppUser(RequestAppUserDto user) {

        if (appUserRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User is already registered. Please log in.");
        }

            AppUser newUser = modelMapper.map(user, AppUser.class);
            return appUserRepo.save(newUser); // Return the saved entity
        }






    @Override
    public List<AppUser> getAllAppUsers() {

        List<AppUser> userList = appUserRepo.findAll();
        if(userList.isEmpty()){
            throw  new NoDataFoundException("no users are added to Appusers ");
        }
        return userList;
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("User is not found . please register the user"));
    }

    @Override
    public AppUser deleteAppUser(Long id) {
        Optional<AppUser> existingUser = appUserRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not found. please register the user");
        }
        appUserRepo.deleteById(id);
        return existingUser.get();
    }

    @Override
    public AppUser updateAppUser(Long id, RequestAppUserDto updatedUser) {
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
