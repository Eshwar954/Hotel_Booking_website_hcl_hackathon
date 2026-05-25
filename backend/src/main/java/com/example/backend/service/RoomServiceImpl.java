package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.RoomDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Room;
import com.example.backend.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	public RoomServiceImpl(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public List<RoomDto> listRooms() {
		return roomRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public RoomDto getRoom(Long id) {
		Room r = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
		return toDto(r);
	}

	@Override
	public List<RoomDto> getRoomsByHotel(Long hotelId) {
		return roomRepository.findAll().stream().filter(r -> hotelId.equals(r.getHotelId())).map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public RoomDto createRoom(RoomDto dto) {
		Room r = new Room();
		r.setNumber(dto.getNumber());
		r.setPrice(dto.getPrice());
		r.setHotelId(dto.getHotelId());
		r.setAvailable(dto.getAvailable() == null ? true : dto.getAvailable());
		Room saved = roomRepository.save(r);
		return toDto(saved);
	}

	@Override
	public RoomDto updateRoom(Long id, RoomDto dto) {
		Room r = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
		r.setNumber(dto.getNumber());
		r.setPrice(dto.getPrice());
		r.setHotelId(dto.getHotelId());
		r.setAvailable(dto.getAvailable() == null ? r.getAvailable() : dto.getAvailable());
		Room saved = roomRepository.save(r);
		return toDto(saved);
	}

	@Override
	public void deleteRoom(Long id) {
		Room r = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
		roomRepository.delete(r);
	}

	private RoomDto toDto(Room r) {
		RoomDto dto = new RoomDto();
		dto.setId(r.getId());
		dto.setNumber(r.getNumber());
		dto.setPrice(r.getPrice());
		dto.setHotelId(r.getHotelId());
		dto.setAvailable(r.getAvailable());
		return dto;
	}
}

