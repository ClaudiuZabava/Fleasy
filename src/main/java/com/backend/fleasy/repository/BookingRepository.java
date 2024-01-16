package com.backend.fleasy.repository;

import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

	@Query(nativeQuery = true, value = "select * from booking rez where rez.flight_id = :id")
	List<Booking> findAllByflightId(Integer id);
	
	Optional<Booking> findById(Integer id);
}
