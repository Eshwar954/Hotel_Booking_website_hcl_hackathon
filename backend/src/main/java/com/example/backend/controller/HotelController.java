package com.hotelbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @GetMapping
    public ResponseEntity<?> listHotels() {
        return ResponseEntity.ok("list hotels stub");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotel(@PathVariable Long id) {
        return ResponseEntity.ok("get hotel " + id);
    }
}
