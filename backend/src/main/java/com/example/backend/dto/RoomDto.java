package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long roomId;
    private Long hotelId;
    private String roomType;
    private Double price;
    private Integer onlineAvailableRooms;
    private String description;
}

