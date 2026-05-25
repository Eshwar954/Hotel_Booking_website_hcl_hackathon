package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.HotelDto;

public interface HotelService {
	List<HotelDto> listHotels();
	HotelDto getHotel(Long id);
	HotelDto createHotel(HotelDto dto);
	HotelDto updateHotel(Long id, HotelDto dto);
	void deleteHotel(Long id);
}

