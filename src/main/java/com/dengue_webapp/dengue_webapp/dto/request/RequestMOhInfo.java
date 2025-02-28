package com.dengue_webapp.dengue_webapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMOhInfo {
    private Long id;
    private String district;
    private String branch;
}
