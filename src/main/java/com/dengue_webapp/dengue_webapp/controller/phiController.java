package com.dengue_webapp.dengue_webapp.controller;
import com.dengue_webapp.dengue_webapp.service.phiService;
import com.dengue_webapp.dengue_webapp.model.phi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phi")
@CrossOrigin

public class phiController {
    @Autowired
    private phiService phiService;

    @GetMapping("/display_phi")
    public List<phi> phi_list(){
        return phiService.getAllStudents();
    }
}
