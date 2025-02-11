package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PHIRepo extends JpaRepository<PHIOfficer, Long> {

    public List<PHIOfficer> findAllByDistrictAndBranch(String district,String branch) ;
    @Query(value = "SELECT * FROM phi WHERE name LIKE ?1 OR id LIKE ?1", nativeQuery = true)
    public List<PHIOfficer> searchPHIOfficer(String searchText, Pageable pageable);

    boolean existsByAppuser_Email(String email);
}
