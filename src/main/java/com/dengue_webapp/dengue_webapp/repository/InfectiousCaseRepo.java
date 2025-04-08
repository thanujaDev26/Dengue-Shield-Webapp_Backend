package com.dengue_webapp.dengue_webapp.repository;


import com.dengue_webapp.dengue_webapp.model.entity.InfectiousCaseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface InfectiousCaseRepo extends JpaRepository<InfectiousCaseReport,Long> {
}
