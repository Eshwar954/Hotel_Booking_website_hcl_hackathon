package com.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelbooking.model.RoomAmenity;

@Repository
public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, Long> {
}
