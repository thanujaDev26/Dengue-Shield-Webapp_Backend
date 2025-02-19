package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.entity.PreApprovedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PreApprovedUserRepo extends JpaRepository<PreApprovedUser,Long> {
    boolean existsByEmail(String email);

    PreApprovedUser findByEmail(String email);
}
