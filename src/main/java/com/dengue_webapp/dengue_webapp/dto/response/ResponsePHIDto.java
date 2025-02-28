package com.dengue_webapp.dengue_webapp.dto.response;


import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponsePHIDto {

    private long id;
    private String name;
    private String mobilenumber;
    private String email;
    private String area;
    private String district;
    private String branch;


}
