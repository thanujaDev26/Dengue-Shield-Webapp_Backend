package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.entity.CommunicableDiseaseNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CommunicableDiseaseNotificationRepo extends JpaRepository<CommunicableDiseaseNotification,Long> {
    CommunicableDiseaseNotification findByPatient_Id(String id);
}
