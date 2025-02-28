package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.entity.AppUser;
import com.dengue_webapp.dengue_webapp.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    boolean existsByEmail(String email);


    List<AppUser> findAllByRole(Role roleName);

    Optional<AppUser> findById(Long id);

    AppUser findByEmail(String email);
}
