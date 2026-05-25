package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}

