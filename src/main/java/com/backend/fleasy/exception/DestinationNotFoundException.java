package com.backend.fleasy.exception;

public class DestinationNotFoundException extends RuntimeException {

	public DestinationNotFoundException(int id) {
		super("Destination with id " + id + " doesn't exist ");
	}
}
