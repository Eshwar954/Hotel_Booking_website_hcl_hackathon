package com.example.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.BookingDto;
import com.example.backend.dto.RoomDto;
import com.example.backend.model.Booking;
import com.example.backend.model.Room;
import com.example.backend.model.User;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;

	public AdminServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
	}

	@Override
	public Object getDashboard() {
		long hotels = 0; // placeholder, can extend
		long rooms = roomRepository.count();
		long bookings = bookingRepository.count();
		long users = userRepository.count();
		return Map.of("rooms", rooms, "bookings", bookings, "users", users);
	}

	@Override
	public List<BookingDto> listBookings() {
		return bookingRepository.findAll().stream().map(b -> {
			BookingDto dto = new BookingDto();
			dto.setUserId(b.getUserId());
			dto.setRoomId(b.getRoomId());
			dto.setFromDate(b.getFromDate());
			dto.setToDate(b.getToDate());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public RoomDto setRoomAvailability(Long roomId, boolean available) {
		Room r = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
		r.setAvailable(available);
		Room saved = roomRepository.save(r);
		RoomDto dto = new RoomDto();
		dto.setId(saved.getId());
		dto.setNumber(saved.getNumber());
		dto.setPrice(saved.getPrice());
		dto.setHotelId(saved.getHotelId());
		dto.setAvailable(saved.getAvailable());
		return dto;
	}
}
