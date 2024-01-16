package com.backend.fleasy.mapper;

import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.PassengerRequest;
import com.backend.fleasy.model.Passenger;

@Component
public class PassengerMapper {

	public Passenger userRequestToUser(PassengerRequest passengerRequest) {

		return new Passenger(passengerRequest.getEmail(), passengerRequest.getLastName(), passengerRequest.getFirstName());
	}
}
