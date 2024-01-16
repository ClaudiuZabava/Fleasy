package com.backend.fleasy.controller;

import com.backend.fleasy.model.Passenger;
import com.backend.fleasy.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.PassengerRequest;
import com.backend.fleasy.mapper.PassengerMapper;
import com.backend.fleasy.model.Booking;
import com.backend.fleasy.service.BookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import io.swagger.annotations.*;

import org.springframework.http.*;

@RestController
@RequestMapping("/users")
@Api(value = "/users", tags = "Users")
public class PassengerController {

	private PassengerService passengerService;
	private BookingService bookingService;

	private PassengerMapper passengerMapper;

	public PassengerController(PassengerService passengerService, BookingService bookingService, PassengerMapper passengerMapper) {
		this.passengerService = passengerService;
		this.bookingService = bookingService;
		this.passengerMapper = passengerMapper;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a user", notes = "Creates a new user based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Passenger was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Passenger> create(
			@Valid @RequestBody @ApiParam(name = "user", value = "Passenger details", required = true) PassengerRequest passengerRequest) {
		Passenger savedPassenger = passengerService.createUser(passengerMapper.userRequestToUser(passengerRequest));
		return ResponseEntity.created(URI.create("/users/" + savedPassenger.getId())).body(savedPassenger);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a user", notes = "Get the details for a user based on the information from the database and the user's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The user was found"),
			@ApiResponse(code = 404, message = "The user was not found") })
	public ResponseEntity<Passenger> getpassenger(@PathVariable Integer id) {
		return ResponseEntity.ok().body(passengerService.getpassenger(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Delete a user", notes = "Delete a user by id from the database and it's bookings")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The user was found"),
			@ApiResponse(code = 404, message = "The user was not found") })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
		List<Booking> bookings = passengerService.getbookingByUser(id);
		for (Booking value : bookings) {
			bookingService.deletebooking(value.getId());
		}

		passengerService.deleteUser(id);
		return ResponseEntity.ok().body("Succesfully deleted");
	}

}
