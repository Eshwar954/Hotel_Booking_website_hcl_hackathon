package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDto {
    private Long hotelId;
    private String hotelName;
    private String location;
    private String description;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String imageUrl;
}

