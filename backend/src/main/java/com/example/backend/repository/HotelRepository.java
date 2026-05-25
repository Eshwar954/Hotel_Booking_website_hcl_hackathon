package com.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelbooking.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
