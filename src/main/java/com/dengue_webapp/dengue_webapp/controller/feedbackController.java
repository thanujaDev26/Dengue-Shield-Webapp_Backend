package com.dengue_webapp.dengue_webapp.controller;

import com.dengue_webapp.dengue_webapp.model.Feedback;
import com.dengue_webapp.dengue_webapp.service.FeedBackServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
public class feedbackController {

    @Autowired
    private FeedBackServiceIMPL feedBackService;

    @PostMapping("/save-feedback")
    public ResponseEntity<Feedback> saveFeedback(@RequestBody Feedback feedback) {
        try {
            // Save feedback and return it in the response body
            Feedback savedFeedback = feedBackService.saveFeedback(feedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate HTTP status
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
