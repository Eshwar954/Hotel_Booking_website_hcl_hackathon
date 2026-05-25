package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.AmenityDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Amenity;
import com.example.backend.model.Room;
import com.example.backend.model.RoomAmenity;
import com.example.backend.repository.AmenityRepository;
import com.example.backend.repository.RoomAmenityRepository;
import com.example.backend.repository.RoomRepository;

@Service
public class AmenityServiceImpl
        implements AmenityService {

    private final AmenityRepository
            amenityRepository;

    private final RoomAmenityRepository
            roomAmenityRepository;

    private final RoomRepository
            roomRepository;

    public AmenityServiceImpl(
            AmenityRepository amenityRepository,
            RoomAmenityRepository roomAmenityRepository,
            RoomRepository roomRepository
    ) {

        this.amenityRepository =
                amenityRepository;

        this.roomAmenityRepository =
                roomAmenityRepository;

        this.roomRepository =
                roomRepository;
    }

    @Override
    public List<AmenityDto>
    listAmenities() {

        return amenityRepository
                .findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AmenityDto createAmenity(
            AmenityDto dto
    ) {

        Amenity amenity =
                new Amenity();

        amenity.setAmenityName(
                dto.getAmenityName()
        );

        Amenity saved =
                amenityRepository.save(
                        amenity
                );

        return mapToDto(saved);
    }

    @Override
    public void assignAmenityToRoom(
            Long roomId,
            Long amenityId
    ) {

        Room room =
                roomRepository
                        .findById(roomId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room",
                                        "id",
                                        roomId
                                )
                        );

        Amenity amenity =
                amenityRepository
                        .findById(amenityId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Amenity",
                                        "id",
                                        amenityId
                                )
                        );

        RoomAmenity roomAmenity =
                new RoomAmenity();

        roomAmenity.setRoom(room);

        roomAmenity.setAmenity(
                amenity
        );

        roomAmenityRepository.save(
                roomAmenity
        );
    }

    private AmenityDto mapToDto(
            Amenity amenity
    ) {

        AmenityDto dto =
                new AmenityDto();

        dto.setAmenityId(
                amenity.getAmenityId()
        );

        dto.setAmenityName(
                amenity.getAmenityName()
        );

        return dto;
    }
}