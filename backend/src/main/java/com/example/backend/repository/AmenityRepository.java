package com.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelbooking.model.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
