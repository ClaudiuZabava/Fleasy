package com.backend.fleasy.exception;

public class PassengerNotFoundException extends RuntimeException {

	public PassengerNotFoundException(int id) {
		super("Passenger with id " + id + " doesn't exist ");
	}
}
