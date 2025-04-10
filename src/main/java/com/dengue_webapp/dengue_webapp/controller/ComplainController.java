package com.dengue_webapp.dengue_webapp.controller;

import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/complain")
public class ComplainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/getAllComplains")
    public ResponseEntity<StandardResponse> getAllComplains() {
        String sql = "SELECT * FROM Complains";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        StandardResponse response = new StandardResponse(200, "Fetched all  users", list);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deleteComplains/{id}")
    public ResponseEntity<StandardResponse> deleteComplainById(@PathVariable Integer id) {
        // SQL query to delete the complaint by ID
        String sql = "DELETE FROM Complains WHERE complaintId = ?";

        // Execute the query
        int rowsAffected = jdbcTemplate.update(sql, id);

        // Check if the deletion was successful
        if (rowsAffected > 0) {
            StandardResponse response = new StandardResponse(200, "Complaint deleted successfully", null);
            return ResponseEntity.ok(response);
        } else {
            StandardResponse response = new StandardResponse(404, "Complaint not found", null);
            return ResponseEntity.status(404).body(response);
        }
    }
}
