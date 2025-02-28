package com.dengue_webapp.dengue_webapp.repository;


import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MOHRepo  extends JpaRepository<MOHOfficer,Long> {

    MOHOfficer findByDistrictAndBranch(String district, String branch);

    boolean existsByAppuser_Email(String email);
}
