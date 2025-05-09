package com.dengue_webapp.dengue_webapp.repository;

import com.dengue_webapp.dengue_webapp.model.entity.MOHOfficer;
import com.dengue_webapp.dengue_webapp.model.entity.Message;
import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MessageRepo extends JpaRepository<Message,Long> {




    List<Message> findAllByMohOfficer(MOHOfficer mohOfficer);



    List<Message> findAllByPhiOfficer_IdAndStatusEquals(Long phiId, MessageStatus pending);
}
