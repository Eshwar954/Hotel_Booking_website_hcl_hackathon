package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.BookingDto;

public interface BookingService {
	BookingDto createBooking(BookingDto dto);
	List<BookingDto> getMyBookings(String username);
	List<BookingDto> listBookings();
	BookingDto getBooking(Long id);
	BookingDto updateBookingStatus(Long id, String status);
	void deleteBooking(Long id);
}

