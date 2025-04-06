package com.dengue_webapp.dengue_webapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InfectiousCaseReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message",nullable = false)
    private Message message;

    private LocalDate completedDate = LocalDate.from(LocalDateTime.now());

}
