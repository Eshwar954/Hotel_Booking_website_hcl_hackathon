package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Hotel;
import com.example.backend.model.Room;

@Repository
public interface RoomRepository
        extends JpaRepository<Room, Long> {

    List<Room> findByHotel(
            Hotel hotel
    );
}