package com.backend.fleasy.exception;

public class ScheduleNotFoundException extends RuntimeException {

	public ScheduleNotFoundException(int id) {
		super("Schedule with id " + id + " doesn't exist ");
	}
}
