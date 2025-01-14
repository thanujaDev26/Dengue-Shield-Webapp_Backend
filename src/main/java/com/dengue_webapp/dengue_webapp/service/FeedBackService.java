package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.model.Feedback;
import org.springframework.stereotype.Service;


public interface FeedBackService {
    Feedback saveFeedback(Feedback feedback);
}
