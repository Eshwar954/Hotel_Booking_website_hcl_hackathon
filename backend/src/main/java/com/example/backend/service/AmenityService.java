package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.AmenityDto;

public interface AmenityService {
	List<AmenityDto> listAmenities();
	AmenityDto createAmenity(AmenityDto dto);
	void assignAmenityToRoom(Long roomId, Long amenityId);
}
