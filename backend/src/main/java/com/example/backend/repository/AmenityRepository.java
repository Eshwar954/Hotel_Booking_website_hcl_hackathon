package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}

