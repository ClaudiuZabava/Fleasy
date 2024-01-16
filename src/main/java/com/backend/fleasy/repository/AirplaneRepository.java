package com.backend.fleasy.repository;

import java.util.Optional;

import com.backend.fleasy.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer>{

	Optional<Airplane> findByName(String name);
}
