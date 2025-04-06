package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestAppUserDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestLoginDto;
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
    public String deleteAppUser(Long id,String role) {
        Optional<AppUser> appUser ;
        String message = "";
        if ("ROLE_MOH".equals(role)) {

            Optional<MOHOfficer>user = mohRepo.findById(id);
            if (user.isEmpty()) {
                throw new NoDataFoundException("No MOH officer found. Please register...");

            }
            appUser = appUserRepo.findById(user.get().getAppuser().getId());
            if (appUser.isEmpty()) {
                throw new NoDataFoundException("No Appuser found. Please register...");

            }
            mohRepo.delete(user.get());
            appUserRepo.delete(appUser.get());

            message = "successfully deleted MOH";
        } else if ("ROLE_PHIO".equals(role)) {

            Optional<PHIOfficer>user = phiRepo.findById(id);
            if (user.isEmpty()) {
                throw new NoDataFoundException("No PHI officer found. Please register...");

            }
            appUser = appUserRepo.findById(user.get().getAppuser().getId());
            if (appUser.isEmpty()) {
                throw new NoDataFoundException("No Appuser found. Please register...");

            }
            phiRepo.delete(user.get());
            appUserRepo.delete(appUser.get());
            message = "successfully deleted PHI";
        }




        return message;
    }

    @Override
    public Object updateAppUser(Long id, String role,Map<String ,Object> Updates) {
        Optional<?> existingUser = Optional.empty(); // Use Optional for cleaner handling

        if ("ROLE_MOH".equals(role)) {

            existingUser = mohRepo.findById(id);
        } else if ("ROLE_PHIO".equals(role)) {

            existingUser = phiRepo.findById(id);
        }
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a Admin user. please register the user");
        }
       // Object userToUpdate = existingUser.get();
        Optional<?> finalExistingUser = existingUser;
//        Updates.forEach((key, value) -> {
//                 switch (key){
//                     case "name" :
//                         finalExistingUser.get().getAppuser().setName((String) value);
//                         break;
//                     case "email" :
//                         finalExistingUser.get().setEmail((String) value);
//                         break;
//                     case "password" :
//                         finalExistingUser.get().setPassword((String) value);
//                         break;
//
//                     default:
//                         throw new InvalidArgumentExeception("Invalid feild name " +key);
//                 }
//
//                }
//
//        );
//
//        AppUser updatedUserResponse = appUserRepo.save( existingUser.get());
//        return updatedUserResponse;
        return null;
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

    @Override
    public Object getAppUserByIdAndRole(Long id, String role) {
        Optional<?> user = Optional.empty(); // Use Optional for cleaner handling
       // System.out.println("in sevice impl");
        if ("ROLE_MOH".equals(role)) {

            user = mohRepo.findById(id);
           // System.out.println(user);
        } else if ("ROLE_PHI".equals(role)) {

            user = phiRepo.findById(id);
           // System.out.println(user);
        }


        if (user.isEmpty()) {
            throw new NoDataFoundException("No MOH or PHI officer found. Please register...");
        }

        return user.get();
    }


}
