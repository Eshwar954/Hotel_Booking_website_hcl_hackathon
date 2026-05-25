package com.example.backend.dto;

public class RoomDto {
    private Long id;
    private String number;
    private Double price;
    private Long hotelId;
    private Boolean available;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}

