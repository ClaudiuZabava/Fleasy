package com.backend.fleasy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ DuplicateUserException.class })
	public ResponseEntity handle(DuplicateUserException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler({ PassengerNotFoundException.class })
	public ResponseEntity<String> handle(PassengerNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ DestinationNotFoundException.class })
	public ResponseEntity<String> handle(DestinationNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ AirplaneNotFoundException.class })
	public ResponseEntity<String> handle(AirplaneNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ FlightNotFoundException.class })
	public ResponseEntity<String> handle(FlightNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ ScheduleNotFoundException.class })
	public ResponseEntity<String> handle(ScheduleNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}
	
	@ExceptionHandler({ BookingNotFoundException.class })
	public ResponseEntity<String> handle(BookingNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest()
				.body("Invalid value : " + e.getFieldError().getRejectedValue() + " for field "
						+ e.getFieldError().getField() + " with message " + e.getFieldError().getDefaultMessage());
	}
}
