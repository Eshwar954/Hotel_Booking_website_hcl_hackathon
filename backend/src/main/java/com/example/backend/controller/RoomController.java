package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @GetMapping
    public ResponseEntity<?> listRooms() {
        return ResponseEntity.ok("list rooms stub");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok("get room " + id);
    }
}
