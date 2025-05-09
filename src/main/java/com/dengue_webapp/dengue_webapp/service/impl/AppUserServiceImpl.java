package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestLoginDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestUserDto;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.AppUserRepo;
import com.dengue_webapp.dengue_webapp.repository.MOHRepo;
import com.dengue_webapp.dengue_webapp.repository.PHIRepo;
import com.dengue_webapp.dengue_webapp.repository.PreApprovedUserRepo;
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
    private PreApprovedUserRepo preApprovedUserRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MOHRepo mohRepo;

    @Autowired
    private PHIRepo phiRepo;
    @Override
    public AppUser registerUser (RequestAppUserDto user) {
        if (appUserRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User is already registered. Please log in.");
        }
        PreApprovedUser preApprovedUser = preApprovedUserRepo.findByEmail(user.getEmail());

        if (preApprovedUser == null) {
            throw new NoDataFoundException("User Email is not registered as MOH or PHI");
        }

        // Map DTO to AppUser entity
        AppUser newUser = modelMapper.map(user, AppUser.class);
        newUser.setRole(preApprovedUser.getRole());
        try {
            appUserRepo.save(newUser);

        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace
        }        System.out.println(newUser);

        if (newUser.getRole().toString().equals(Role.ROLE_MOH.toString())) {
            MOHOfficer mohOfficer = new MOHOfficer();
            mohOfficer.setAppuser(newUser);
            mohRepo.save(mohOfficer);
        } else {
            PHIOfficer phiOfficer = new PHIOfficer();
            phiOfficer.setAppuser(newUser);
            phiRepo.save(phiOfficer);
        }

        return newUser;
    }



    //need to hash the password.
             // Return the saved entity




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

    @Override
    public Object loginAppUser(RequestLoginDto user) {
        // Check if the user exists
        AppUser registeredUser = appUserRepo.findByEmail(user.getEmail());
        if (registeredUser == null) {
            throw new NoDataFoundException("User is not registered, please register.");
        }
        System.out.println(registeredUser);
        // Check password (Use a password encoder if passwords are hashed)
        if (!registeredUser.getPassword().equals(user.getPassword())) {
            throw new InvalidArgumentExeception("Incorrect password.");
        }

        // Check role and return appropriate user
        if ("ROLE_MOH".equals(registeredUser.getRole().toString())) {
            MOHOfficer mohUser = mohRepo.findByAppuser_Email(registeredUser.getEmail());
            if (mohUser == null) {
                throw new NoDataFoundException("MOH user not found.");
            }
            System.out.println(mohUser);
            return mohUser;
        } else {
            PHIOfficer phiUser = phiRepo.findByAppuser_Email(registeredUser.getEmail());
            if (phiUser == null) {
                throw new NoDataFoundException("PHI user not found.");
            }
            System.out.println(phiUser);
            return phiUser;
        }
    }


}
