package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.RoomDto;

public interface RoomService {
	List<RoomDto> listRooms();
	RoomDto getRoom(Long id);
	List<RoomDto> getRoomsByHotel(Long hotelId);
	RoomDto createRoom(RoomDto dto);
	RoomDto updateRoom(Long id, RoomDto dto);
	void deleteRoom(Long id);
}

