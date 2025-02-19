package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestPreApprovedUserDto;
import com.dengue_webapp.dengue_webapp.exceptions.InvalidArgumentExeception;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.exceptions.UserAlreadyExistsException;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import com.dengue_webapp.dengue_webapp.repository.PreApprovedUserRepo;
import com.dengue_webapp.dengue_webapp.service.PreApprovedUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PreApprovedUserServiceIMPL implements PreApprovedUserService {
    @Autowired
    private PreApprovedUserRepo preApprovedUserRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PreApprovedUser addPreApprovedUser(RequestPreApprovedUserDto user) {
        // Check if user with the same email already exists
        if (preApprovedUserRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " is already registered.");
        }

        // Convert DTO to Entity
        PreApprovedUser newUser = new PreApprovedUser();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());

        // Save to database
        return preApprovedUserRepo.save(newUser);
    }

    @Override
    public List<PreApprovedUser> getAllPreApprovedUsers() {
        List<PreApprovedUser> userList = preApprovedUserRepo.findAll();
        if(userList.isEmpty()){
            throw  new NoDataFoundException("no PreApprovedusers are added to table");
        }
        return userList;
    }

    @Override
    public PreApprovedUser getPreApprovedUserById(Long id) {
        return preApprovedUserRepo.findById(id)
                .orElseThrow(() -> new NoDataFoundException("No PreApprovedUser found in the table"));
    }

    @Override
    public PreApprovedUser deletePreApprovedUser(Long id) {
        Optional<PreApprovedUser> existingUser = preApprovedUserRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a PreApproved user. please register the user");
        }
        preApprovedUserRepo.deleteById(id);
        return existingUser.get();

    }

    @Override
    public PreApprovedUser updatePreApprovedUser(Long id,  Map<String ,Object> Updates) {
        Optional<PreApprovedUser> existingUser = preApprovedUserRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new NoDataFoundException("user is not a PreApproved user. please register the user");
        }
        PreApprovedUser userToUpdate = existingUser.get();
        Updates.forEach((key,value) -> {
                    switch (key){
                        case "name" :
                            userToUpdate.setName((String) value);
                            break;
                        case "email" :
                            userToUpdate.setEmail((String) value);
                            break;

                        case "role" :
                            userToUpdate.setRole(Role.valueOf((String) value));
                            break;
                        default:
                            throw new InvalidArgumentExeception("Invalid feild name " +key);
                    }

                });
        PreApprovedUser updatedUserResponse = preApprovedUserRepo.save(userToUpdate);
        return updatedUserResponse;
    }

}

