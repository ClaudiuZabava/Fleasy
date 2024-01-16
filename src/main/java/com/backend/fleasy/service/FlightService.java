package com.backend.fleasy.service;

import java.util.Optional;

import com.backend.fleasy.model.Airplane;
import com.backend.fleasy.model.Destination;
import com.backend.fleasy.model.Flight;
import com.backend.fleasy.repository.FlightRepository;
import org.springframework.stereotype.Service;

import com.backend.fleasy.exception.FlightNotFoundException;

@Service
public class FlightService {

	private FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public Flight updateflightairplane(Flight oldFlight, Airplane newAirplane) {
		oldFlight.setairplane(newAirplane);
		return flightRepository.save(oldFlight);
	}

	public Flight createflight(Flight flight, Airplane airplane, Destination destination) {
		flight.setairplane(airplane);
		flight.setdestination(destination);
		return flightRepository.save(flight);
	}

	public Flight getflight(Integer id) {
		Optional<Flight> flightOptional = flightRepository.findById(id);
		if (flightOptional.isPresent()) {
			return flightOptional.get();
		} else {
			throw new FlightNotFoundException(id);
		}
	}

	public void deleteflight(Integer id) {
		Optional<Flight> flightOptional = flightRepository.findById(id);
		if (flightOptional.isPresent()) {
			flightRepository.delete(flightOptional.get());
		} else {
			throw new FlightNotFoundException(id);
		}
	}

}
