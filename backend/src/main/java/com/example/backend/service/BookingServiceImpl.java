package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.BookingDto;
import com.example.backend.model.Booking;
import com.example.backend.model.Room;
import com.example.backend.model.User;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.exception.ResourceNotFoundException;

@Service
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;

	public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
	}

	@Override
	@Transactional
	public BookingDto createBooking(BookingDto dto) {
		User u = userRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
		Room r = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room", "id", dto.getRoomId()));
		if (Boolean.FALSE.equals(r.getAvailable())) {
			throw new RuntimeException("Room not available");
		}
		Booking b = new Booking();
		b.setUserId(u.getId());
		b.setRoomId(r.getId());
		b.setFromDate(dto.getFromDate());
		b.setToDate(dto.getToDate());
		Booking saved = bookingRepository.save(b);
		r.setAvailable(false);
		roomRepository.save(r);
		return toDto(saved);
	}

	@Override
	public List<BookingDto> getMyBookings(String username) {
		User u = userRepository.findAll().stream().filter(x -> username.equals(x.getUsername())).findFirst().orElseThrow(() -> new RuntimeException("User not found"));
		return bookingRepository.findAll().stream().filter(b -> u.getId().equals(b.getUserId())).map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public BookingDto getBooking(Long id) {
		Booking b = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
		return toDto(b);
	}

	@Override
	@Transactional
	public void deleteBooking(Long id) {
		Booking b = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
		Room r = roomRepository.findById(b.getRoomId()).orElse(null);
		if (r != null) {
			r.setAvailable(true);
			roomRepository.save(r);
		}
		bookingRepository.delete(b);
	}

	private BookingDto toDto(Booking b) {
		BookingDto dto = new BookingDto();
		dto.setUserId(b.getUserId());
		dto.setRoomId(b.getRoomId());
		dto.setFromDate(b.getFromDate());
		dto.setToDate(b.getToDate());
		return dto;
	}
}

