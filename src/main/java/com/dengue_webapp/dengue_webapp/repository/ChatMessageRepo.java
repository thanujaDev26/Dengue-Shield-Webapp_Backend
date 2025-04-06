package com.dengue_webapp.dengue_webapp.repository;


import com.dengue_webapp.dengue_webapp.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ChatMessageRepo extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findAllByReceiverId(long id);
}
