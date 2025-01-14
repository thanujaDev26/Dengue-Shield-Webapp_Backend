package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.PHIOfficer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PHIRepo extends JpaRepository<PHIOfficer, String> {
    public List<PHIOfficer> findAllByName(String name);

    @Query(value = "SELECT * FROM phi WHERE name LIKE ?1 OR id LIKE ?1", nativeQuery = true)
    public List<PHIOfficer> searchPHIOfficer(String searchText, Pageable pageable);
}
