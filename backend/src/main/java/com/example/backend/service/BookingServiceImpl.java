package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.BookingDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Booking;
import com.example.backend.model.Room;
import com.example.backend.model.User;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.UserRepository;

@Service
public class BookingServiceImpl
		implements BookingService {

	private final BookingRepository bookingRepository;

	private final UserRepository userRepository;

	private final RoomRepository roomRepository;

	public BookingServiceImpl(
			BookingRepository bookingRepository,
			UserRepository userRepository,
			RoomRepository roomRepository) {

		this.bookingRepository = bookingRepository;

		this.userRepository = userRepository;

		this.roomRepository = roomRepository;
	}

	@Override
	@Transactional
	public BookingDto createBooking(
			BookingDto dto) {

		User user = userRepository
				.findById(
						dto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"User",
						"id",
						dto.getUserId()));

		Room room = roomRepository
				.findById(
						dto.getRoomId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Room",
						"id",
						dto.getRoomId()));

		if (room.getOnlineAvailableRooms() <= 0) {

			throw new RuntimeException(
					"No rooms available");
		}

		Booking booking = new Booking();

		booking.setUser(user);

		booking.setRoom(room);

		booking.setCheckInDate(
				dto.getCheckInDate());

		booking.setCheckOutDate(
				dto.getCheckOutDate());

		booking.setTotalPrice(
				dto.getTotalPrice());

		booking.setStatus(
				"CONFIRMED");

		Booking savedBooking = bookingRepository
				.save(booking);

		room.setOnlineAvailableRooms(
				room.getOnlineAvailableRooms()
						- 1);

		roomRepository.save(room);

		return mapToDto(savedBooking);
	}

	@Override
	public List<BookingDto> getMyBookings(
			String email) {

		User user = userRepository
				.findByEmail(email)
				.orElseThrow(() -> new RuntimeException(
						"User not found"));

		return bookingRepository
				.findByUser(user)
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public BookingDto getBooking(
			Long id) {

		Booking booking = bookingRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Booking",
						"id",
						id));

		return mapToDto(booking);
	}

	@Override
	@Transactional
	public void deleteBooking(
			Long id) {

		Booking booking = bookingRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Booking",
						"id",
						id));

		Room room = booking.getRoom();

		room.setOnlineAvailableRooms(
				room.getOnlineAvailableRooms()
						+ 1);

		roomRepository.save(room);

		bookingRepository.delete(
				booking);
	}

	private BookingDto mapToDto(
			Booking booking) {

		BookingDto dto = new BookingDto();

		dto.setBookingId(
				booking.getBookingId());

		dto.setUserId(
				booking.getUser()
						.getUserId());

		dto.setRoomId(
				booking.getRoom()
						.getRoomId());

		dto.setCheckInDate(
				booking.getCheckInDate());

		dto.setCheckOutDate(
				booking.getCheckOutDate());

		dto.setTotalPrice(
				booking.getTotalPrice());

		dto.setStatus(
				booking.getStatus());

		return dto;
	}
}