package com.dengue_webapp.dengue_webapp.dto.request;

import com.dengue_webapp.dengue_webapp.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAdminDto {

    private String name;

    private String email;

    private String password;

    private Role role;  // ADMIN, MOH, PHI, USER


}

