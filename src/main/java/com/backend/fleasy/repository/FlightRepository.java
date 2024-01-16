package com.backend.fleasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.fleasy.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

}
