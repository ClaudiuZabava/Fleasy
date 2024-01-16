package com.backend.fleasy.exception;

public class FlightNotFoundException extends RuntimeException {
	
	public FlightNotFoundException(int id) {
		super("Flight with id " + id + " doesn't exist ");
	}
}
