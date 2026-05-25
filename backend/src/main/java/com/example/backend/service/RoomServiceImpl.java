package com.example.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.RoomDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Hotel;
import com.example.backend.model.Room;
import com.example.backend.repository.HotelRepository;
import com.example.backend.repository.RoomRepository;

@Service
public class RoomServiceImpl
        implements RoomService {

    private final RoomRepository
            roomRepository;

    private final HotelRepository
            hotelRepository;

    public RoomServiceImpl(
            RoomRepository roomRepository,
            HotelRepository hotelRepository
    ) {

        this.roomRepository =
                roomRepository;

        this.hotelRepository =
                hotelRepository;
    }

    @Override
    public List<RoomDto>
    listRooms() {

        return roomRepository
                .findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoom(
            Long id
    ) {

        Room room =
                roomRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room",
                                        "id",
                                        id
                                )
                        );

        return mapToDto(room);
    }

    @Override
    public List<RoomDto>
    getRoomsByHotel(
            Long hotelId
    ) {

        Hotel hotel =
                hotelRepository
                        .findById(hotelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel",
                                        "id",
                                        hotelId
                                )
                        );

        return roomRepository
                .findByHotel(hotel)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto createRoom(
            RoomDto dto
    ) {

        Hotel hotel =
                hotelRepository
                        .findById(
                                dto.getHotelId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel",
                                        "id",
                                        dto.getHotelId()
                                )
                        );

        Room room =
                new Room();

        room.setHotel(hotel);

        room.setRoomType(
                dto.getRoomType()
        );

        room.setPrice(
                dto.getPrice()
        );

        room.setOnlineAvailableRooms(
                dto.getOnlineAvailableRooms()
        );

        room.setDescription(
                dto.getDescription()
        );

        Room savedRoom =
                roomRepository.save(room);

        return mapToDto(savedRoom);
    }

    @Override
    public RoomDto updateRoom(
            Long id,
            RoomDto dto
    ) {

        Room room =
                roomRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room",
                                        "id",
                                        id
                                )
                        );

        if (dto.getHotelId() != null) {

            Hotel hotel =
                    hotelRepository
                            .findById(
                                    dto.getHotelId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Hotel",
                                            "id",
                                            dto.getHotelId()
                                    )
                            );

            room.setHotel(hotel);
        }

        room.setRoomType(
                dto.getRoomType()
        );

        room.setPrice(
                dto.getPrice()
        );

        room.setOnlineAvailableRooms(
                dto.getOnlineAvailableRooms()
        );

        room.setDescription(
                dto.getDescription()
        );

        Room updatedRoom =
                roomRepository.save(room);

        return mapToDto(updatedRoom);
    }

    @Override
    public void deleteRoom(
            Long id
    ) {

        Room room =
                roomRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room",
                                        "id",
                                        id
                                )
                        );

        roomRepository.delete(room);
    }

    private RoomDto mapToDto(
            Room room
    ) {

        RoomDto dto =
                new RoomDto();

        dto.setRoomId(
                room.getRoomId()
        );

        dto.setHotelId(
                room.getHotel()
                        .getHotelId()
        );

        dto.setRoomType(
                room.getRoomType()
        );

        dto.setPrice(
                room.getPrice()
        );

        dto.setOnlineAvailableRooms(
                room.getOnlineAvailableRooms()
        );

        dto.setDescription(
                room.getDescription()
        );

        return dto;
    }
}