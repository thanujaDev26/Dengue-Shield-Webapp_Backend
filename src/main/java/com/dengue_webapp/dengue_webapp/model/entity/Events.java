package com.dengue_webapp.dengue_webapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EventId;

    @Column(nullable = false,name="title")
    private String title;

    @Column(name="message",nullable = false)
    private String message;

    @Column(name="type",nullable = false)
    private String type;


    @Column(name="date",nullable = false)
    private LocalDateTime date;

    @Column(name="venue",nullable = false)
    private String venue;

    @Column(name = "imageUrls")
    private String imageUrls;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}
