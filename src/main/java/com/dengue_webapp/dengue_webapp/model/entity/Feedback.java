package com.dengue_webapp.dengue_webapp.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Entity
@Table(name = "Feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Feedback {
    @Id
    @Column(name = "id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "subject", length = 255)
    private String subject;
    @Column(name = "message", length = 255)
    private String message;
}

