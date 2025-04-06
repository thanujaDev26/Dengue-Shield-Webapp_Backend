package com.dengue_webapp.dengue_webapp.repository;


import com.dengue_webapp.dengue_webapp.model.entity.H411;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface H411Repo extends JpaRepository<H411,Long> {
}
