package com.hotelbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.dto.BookingDto;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDto dto) {
        return ResponseEntity.ok("create booking stub");
    }
}
