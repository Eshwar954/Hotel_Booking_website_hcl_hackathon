package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.BookingDto;

public interface BookingService {
	BookingDto createBooking(BookingDto dto);
	List<BookingDto> getMyBookings(String username);
	BookingDto getBooking(Long id);
	void deleteBooking(Long id);
}

