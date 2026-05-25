package com.hotelbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @GetMapping
    public ResponseEntity<?> listAmenities() {
        return ResponseEntity.ok("list amenities stub");
    }
}
