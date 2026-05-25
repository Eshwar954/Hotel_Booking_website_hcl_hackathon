package com.hotelbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @RequestMapping("/status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok("admin stub");
    }
}
