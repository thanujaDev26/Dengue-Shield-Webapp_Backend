package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.model.entity.Feedback;
import com.dengue_webapp.dengue_webapp.repository.FeedBackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedBackServiceIMPL implements FeedBackService {
   @Autowired
   private FeedBackRepo feedBackRepo;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedBackRepo.save(feedback);
    }
}
