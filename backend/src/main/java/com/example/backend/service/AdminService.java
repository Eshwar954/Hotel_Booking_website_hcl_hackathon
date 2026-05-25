package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.BookingDto;
import com.example.backend.dto.RoomDto;
import com.example.backend.model.User;

public interface AdminService {

    Object getDashboard();

    List<BookingDto> listBookings();

    List<User> listUsers();

    RoomDto updateRoomAvailability(Long roomId,Integer onlineAvailableRooms);
}