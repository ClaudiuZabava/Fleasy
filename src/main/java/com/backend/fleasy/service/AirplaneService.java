package com.backend.fleasy.service;

import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.Airplane;
import com.backend.fleasy.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import com.backend.fleasy.exception.AirplaneNotFoundException;
import com.backend.fleasy.model.Seat;

@Service
public class AirplaneService {
	private AirplaneRepository airplaneRepository;

	public AirplaneService(AirplaneRepository airplaneRepository) {
		this.airplaneRepository = airplaneRepository;
	}

	public Airplane saveSeats(List<Seat> seats, Airplane airplane) {
		airplane.setSeats(seats);
		return airplaneRepository.save(airplane);
	}

	public Airplane createairplane(Airplane airplane) {
		return airplaneRepository.save(airplane);
	}

	public Airplane getairplane(Integer id) {
		Optional<Airplane> airplaneOptional = airplaneRepository.findById(id);
		if (airplaneOptional.isPresent()) {
			return airplaneOptional.get();
		} else {
			throw new AirplaneNotFoundException(id);
		}
	}
	
	public List<Airplane> getAllairplanes() {
		return airplaneRepository.findAll();
	}
}
