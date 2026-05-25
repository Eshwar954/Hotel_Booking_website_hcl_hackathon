package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.AmenityDto;
import com.example.backend.model.Amenity;
import com.example.backend.model.RoomAmenity;
import com.example.backend.repository.AmenityRepository;
import com.example.backend.repository.RoomAmenityRepository;

@Service
public class AmenityServiceImpl implements AmenityService {

	private final AmenityRepository amenityRepository;
	private final RoomAmenityRepository roomAmenityRepository;

	public AmenityServiceImpl(AmenityRepository amenityRepository, RoomAmenityRepository roomAmenityRepository) {
		this.amenityRepository = amenityRepository;
		this.roomAmenityRepository = roomAmenityRepository;
	}

	@Override
	public List<AmenityDto> listAmenities() {
		return amenityRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public AmenityDto createAmenity(AmenityDto dto) {
		Amenity a = new Amenity();
		a.setName(dto.getName());
		Amenity saved = amenityRepository.save(a);
		return toDto(saved);
	}

	@Override
	public void assignAmenityToRoom(Long roomId, Long amenityId) {
		RoomAmenity ra = new RoomAmenity();
		ra.setRoomId(roomId);
		ra.setAmenityId(amenityId);
		roomAmenityRepository.save(ra);
	}

	private AmenityDto toDto(Amenity a) {
		AmenityDto dto = new AmenityDto();
		dto.setId(a.getId());
		dto.setName(a.getName());
		return dto;
	}
}
