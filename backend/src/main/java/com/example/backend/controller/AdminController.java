package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.BookingDto;
import com.example.backend.dto.RoomDto;
import com.example.backend.dto.UpdateRoomAvailabilityDto;
import com.example.backend.dto.UserDto;
import com.example.backend.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> bookings() {
        return ResponseEntity.ok(adminService.listBookings());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users() {
        return ResponseEntity.ok(adminService.listUsers());
    }

    @PutMapping("/rooms/{roomId}/availability")
    public ResponseEntity<RoomDto> setRoomAvailability(
            @PathVariable Long roomId,
            @RequestBody UpdateRoomAvailabilityDto updateDto) {

        RoomDto dto = adminService.updateRoomAvailability(
                roomId,
                updateDto.getOnlineAvailableRooms());

        return ResponseEntity.ok(dto);
    }
}
