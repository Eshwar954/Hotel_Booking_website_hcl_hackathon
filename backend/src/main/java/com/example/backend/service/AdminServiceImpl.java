package com.example.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.BookingDto;
import com.example.backend.dto.RoomDto;
import com.example.backend.dto.UserDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Booking;
import com.example.backend.model.Room;
import com.example.backend.model.User;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.RoomRepository;
import com.example.backend.repository.UserRepository;

@Service
public class AdminServiceImpl
        implements AdminService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    public AdminServiceImpl(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            RoomRepository roomRepository) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Object getDashboard() {
        long rooms = roomRepository.count();
        long bookings = bookingRepository.count();
        long users = userRepository.count();

        return Map.of(
                "rooms", rooms,
                "bookings", bookings,
                "users", users);
    }

    @Override
    public List<BookingDto> listBookings() {
        return bookingRepository
                .findAll()
                .stream()
                .map(this::mapToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto updateRoomAvailability(
            Long roomId,
            Integer onlineAvailableRooms) {

        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room",
                        "id",
                        roomId));

        room.setOnlineAvailableRooms(
                onlineAvailableRooms);

        Room saved = roomRepository.save(room);
        return mapToRoomDto(saved);
    }

    private BookingDto mapToBookingDto(
            Booking booking) {

        BookingDto dto = new BookingDto();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setRoomId(booking.getRoom().getRoomId());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    private RoomDto mapToRoomDto(
            Room room) {

        RoomDto dto = new RoomDto();
        dto.setRoomId(room.getRoomId());
        dto.setHotelId(room.getHotel().getHotelId());
        dto.setRoomType(room.getRoomType());
        dto.setPrice(room.getPrice());
        dto.setOnlineAvailableRooms(room.getOnlineAvailableRooms());
        dto.setDescription(room.getDescription());
        return dto;
    }
}