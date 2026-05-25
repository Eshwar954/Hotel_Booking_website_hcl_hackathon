package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @Column(nullable = false)
    private String hotelName;

    private String location;

    @Column(length = 1000)
    private String description;

    private String ownerName;

    private String ownerEmail;

    private String ownerPhone;

    private String imageUrl;
}