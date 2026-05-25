package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.HotelDto;
import com.example.backend.model.Hotel;
import com.example.backend.repository.HotelRepository;
import com.example.backend.exception.ResourceNotFoundException;

@Service
public class HotelServiceImpl implements HotelService {

	private final HotelRepository hotelRepository;

	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public List<HotelDto> listHotels() {
		return hotelRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public HotelDto getHotel(Long id) {
		Hotel h = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));
		return toDto(h);
	}

	@Override
	public HotelDto createHotel(HotelDto dto) {
		Hotel h = new Hotel();
		h.setName(dto.getName());
		h.setAddress(dto.getAddress());
		Hotel saved = hotelRepository.save(h);
		return toDto(saved);
	}

	@Override
	public HotelDto updateHotel(Long id, HotelDto dto) {
		Hotel h = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));
		h.setName(dto.getName());
		h.setAddress(dto.getAddress());
		Hotel saved = hotelRepository.save(h);
		return toDto(saved);
	}

	@Override
	public void deleteHotel(Long id) {
		Hotel h = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", id));
		hotelRepository.delete(h);
	}

	private HotelDto toDto(Hotel h) {
		HotelDto dto = new HotelDto();
		dto.setId(h.getId());
		dto.setName(h.getName());
		dto.setAddress(h.getAddress());
		return dto;
	}
}

