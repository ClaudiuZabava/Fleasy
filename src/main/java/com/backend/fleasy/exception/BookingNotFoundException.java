package com.backend.fleasy.exception;

public class BookingNotFoundException extends RuntimeException {

	public BookingNotFoundException(int id) {
		super("Booking with id " + id + " doesn't exist ");
	}
}
