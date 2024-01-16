package com.backend.fleasy.exception;

public class AirplaneNotFoundException extends RuntimeException {

	public AirplaneNotFoundException(int id) {
		super("Airplane with id " + id + " doesn't exist ");
	}
}
