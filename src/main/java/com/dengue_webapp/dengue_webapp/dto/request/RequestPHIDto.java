package com.dengue_webapp.dengue_webapp.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestPHIDto {

    private String name;

    private String phone;

    private String email;

    private String area;

    private String district;

    private String branch;

}
