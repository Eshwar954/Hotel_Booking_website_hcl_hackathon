package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.HotelDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Hotel;
import com.example.backend.repository.HotelRepository;

@Service
public class HotelServiceImpl
		implements HotelService {

	private final HotelRepository hotelRepository;

	public HotelServiceImpl(
			HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public List<HotelDto> listHotels() {

		return hotelRepository
				.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public HotelDto getHotel(
			Long id) {

		Hotel hotel = hotelRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Hotel",
						"id",
						id));

		return mapToDto(hotel);
	}

	@Override
	public HotelDto createHotel(
			HotelDto dto) {

		Hotel hotel = new Hotel();

		hotel.setHotelName(
				dto.getHotelName());

		hotel.setLocation(
				dto.getLocation());

		hotel.setDescription(
				dto.getDescription());

		hotel.setOwnerName(
				dto.getOwnerName());

		hotel.setOwnerEmail(
				dto.getOwnerEmail());

		hotel.setOwnerPhone(
				dto.getOwnerPhone());

		hotel.setImageUrl(
				dto.getImageUrl());

		Hotel savedHotel = hotelRepository.save(hotel);

		return mapToDto(savedHotel);
	}

	@Override
	public HotelDto updateHotel(
			Long id,
			HotelDto dto) {

		Hotel hotel = hotelRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Hotel",
						"id",
						id));

		hotel.setHotelName(
				dto.getHotelName());

		hotel.setLocation(
				dto.getLocation());

		hotel.setDescription(
				dto.getDescription());

		hotel.setOwnerName(
				dto.getOwnerName());

		hotel.setOwnerEmail(
				dto.getOwnerEmail());

		hotel.setOwnerPhone(
				dto.getOwnerPhone());

		hotel.setImageUrl(
				dto.getImageUrl());

		Hotel updatedHotel = hotelRepository.save(hotel);

		return mapToDto(updatedHotel);
	}

	@Override
	public void deleteHotel(
			Long id) {

		Hotel hotel = hotelRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Hotel",
						"id",
						id));

		hotelRepository.delete(hotel);
	}

	private HotelDto mapToDto(
			Hotel hotel) {

		HotelDto dto = new HotelDto();

		dto.setHotelId(
				hotel.getHotelId());

		dto.setHotelName(
				hotel.getHotelName());

		dto.setLocation(
				hotel.getLocation());

		dto.setDescription(
				hotel.getDescription());

		dto.setOwnerName(
				hotel.getOwnerName());

		dto.setOwnerEmail(
				hotel.getOwnerEmail());

		dto.setOwnerPhone(
				hotel.getOwnerPhone());

		dto.setImageUrl(
				hotel.getImageUrl());

		return dto;
	}
}