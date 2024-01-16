package com.backend.fleasy.service;

import java.util.List;
import java.util.Optional;

import com.backend.fleasy.model.Booking;
import org.springframework.stereotype.Service;

import com.backend.fleasy.exception.DuplicateUserException;
import com.backend.fleasy.exception.PassengerNotFoundException;
import com.backend.fleasy.model.Passenger;
import com.backend.fleasy.repository.PassengerRepository;

@Service
public class PassengerService {

	private PassengerRepository passengerRepository;

	public PassengerService(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}

	public Passenger createUser(Passenger passenger) {
		Optional<Passenger> existingUserSameEmail = passengerRepository.findByEmail(passenger.getEmail());
		existingUserSameEmail.ifPresent(e -> {
			throw new DuplicateUserException();
		});
		return passengerRepository.save(passenger);
	}

	public Passenger getpassenger(Integer id) {
		Optional<Passenger> userOptional = passengerRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new PassengerNotFoundException(id);
		}
	}

	public void deleteUser(Integer id) {
		Optional<Passenger> userOptional = passengerRepository.findById(id);
		if (userOptional.isPresent()) {
			passengerRepository.delete(userOptional.get());
		} else {
			throw new PassengerNotFoundException(id);
		}
	}

	public List<Booking> getbookingByUser(Integer id) {
		Optional<Passenger> userOptional = passengerRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get().getbookings();
		} else {
			throw new PassengerNotFoundException(id);
		}
	}
}
