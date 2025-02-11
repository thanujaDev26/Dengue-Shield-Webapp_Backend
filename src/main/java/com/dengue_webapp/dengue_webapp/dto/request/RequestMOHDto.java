package com.dengue_webapp.dengue_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMOHDto {


    private String name;
    private String email;
    private String mobilenumber;
    private String district;
    private String branch;
}
