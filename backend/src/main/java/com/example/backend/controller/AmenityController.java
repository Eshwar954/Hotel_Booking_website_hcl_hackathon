package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.AmenityDto;
import com.example.backend.service.AmenityService;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<List<AmenityDto>> listAmenities() {
        return ResponseEntity.ok(amenityService.listAmenities());
    }

    @PostMapping
    public ResponseEntity<AmenityDto> createAmenity(@RequestBody AmenityDto dto) {
        return ResponseEntity.ok(amenityService.createAmenity(dto));
    }

    @PostMapping("/rooms/{roomId}/amenities/{amenityId}")
    public ResponseEntity<?> assignAmenityToRoom(@PathVariable Long roomId, @PathVariable Long amenityId) {
        amenityService.assignAmenityToRoom(roomId, amenityId);
        return ResponseEntity.ok().build();
    }
}
