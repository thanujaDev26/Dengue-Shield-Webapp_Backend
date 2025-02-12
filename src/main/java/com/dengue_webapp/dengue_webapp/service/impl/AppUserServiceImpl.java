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
import java.util.Map;
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
            //need to hash the password.
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
        Optional<AppUser> existingUser = appUserRepo.findById(id);
        System.out.println(existingUser.get());
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not found. please register the user");
        }

        return existingUser.get();

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
    public AppUser updateAppUser(Long id, Map<String ,Object> Updates) {
        Optional<AppUser> existingUser = appUserRepo.findById(id);
       // System.out.println(existingUser);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a Admin user. please register the user");
        }
        AppUser userToUpdate = existingUser.get();
        Updates.forEach((key,value) -> {
                 switch (key){
                     case "name" :
                         userToUpdate.setName((String) value);
                         break;
                     case "email" :
                         userToUpdate.setEmail((String) value);
                         break;
                     case "password" :
                         userToUpdate.setPassword((String) value);
                         break;
                     case "role" :
                         userToUpdate.setRole(Role.valueOf((String) value));
                         break;
                     default:
                         throw new InvalidArgumentExeception("Invalid feild name " +key);
                 }

                }

        );

        AppUser updatedUserResponse = appUserRepo.save(userToUpdate);
        return updatedUserResponse;
    }

}
