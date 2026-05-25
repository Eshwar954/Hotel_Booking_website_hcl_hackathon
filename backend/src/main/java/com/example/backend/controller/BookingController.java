package com.example.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ApiResponseDto;
import com.example.backend.dto.BookingDto;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto dto, Principal principal) {
        Long userId = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getUserId();
        dto.setUserId(userId);
        return ResponseEntity.ok(bookingService.createBooking(dto));
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<BookingDto>> myBookings(Principal principal) {
        return ResponseEntity.ok(bookingService.getMyBookings(principal.getName()));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingDto>> myBookingsAlias(Principal principal) {
        return ResponseEntity.ok(bookingService.getMyBookings(principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDto>> listBookings() {
        return ResponseEntity.ok(bookingService.listBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BookingDto> updateStatus(@PathVariable Long id, @RequestBody BookingDto dto) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, dto.getStatus()));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<ApiResponseDto> cancelBooking(@PathVariable Long id) {
        bookingService.updateBookingStatus(id, "CANCELLED");
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Booking cancelled successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

