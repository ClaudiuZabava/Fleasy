package com.backend.fleasy.repository;

import java.util.Optional;

import com.backend.fleasy.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

	@Query(value = "select * from user where first_name = :name", nativeQuery = true)
	Passenger findUserByFirstNameWithNativeQuery(String name);

	Optional<Passenger> findByEmail(String email);

}
