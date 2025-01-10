package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.model.phi;
import com.dengue_webapp.dengue_webapp.repository.phiRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class phiServiceIMPL implements phiService {
    @Autowired
    private phiRepository phiRepository;

    @Override
    public List<phi> getAllStudents() {
        return phiRepository.findAll();
    }
}
