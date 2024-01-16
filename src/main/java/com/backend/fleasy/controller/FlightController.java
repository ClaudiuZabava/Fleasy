package com.backend.fleasy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.backend.fleasy.model.Airplane;
import com.backend.fleasy.model.Destination;
import com.backend.fleasy.model.Flight;
import com.backend.fleasy.service.AirplaneService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.FlightRequest;
import com.backend.fleasy.mapper.FlightMapper;

import com.backend.fleasy.model.Booking;
import com.backend.fleasy.service.FlightService;
import com.backend.fleasy.service.DestinationService;
import com.backend.fleasy.service.BookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/flights")
@Api(value = "/flights", tags = "Flights")
public class FlightController {

	private FlightService flightService;
	private AirplaneService airplaneService;
	private DestinationService destinationService;
	private BookingService bookingService;

	private FlightMapper flightMapper;

	public FlightController(FlightService flightService, AirplaneService airplaneService, DestinationService destinationService, BookingService bookingService,
							FlightMapper flightMapper) {
		this.flightService = flightService;
		this.airplaneService = airplaneService;
		this.destinationService = destinationService;
		this.bookingService = bookingService;
		this.flightMapper = flightMapper;
	}

	@PostMapping(path = "/{airplaneId}/{destinationId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a flight", notes = "Creates a new flight based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Flight was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Flight> create(@PathVariable Integer airplaneId, @PathVariable Integer destinationId,
										 @Valid @RequestBody @ApiParam(name = "flight", value = "Flight details", required = true) FlightRequest flightRequest) {
		Airplane airplane = airplaneService.getairplane(airplaneId);
		Destination destination = destinationService.getdestination(destinationId);
		Flight savedFlight = flightService
				.createflight(flightMapper.flightRequestToflight(flightRequest), airplane, destination);

		return ResponseEntity.created(URI.create("/flights/" + savedFlight.getId())).body(savedFlight);
	}

	@PutMapping(path = "/{flightId}/{newairplaneId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update the airplane for a flight", notes = "Update the airplane for a flight based by airplane id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The flight was successfully updated based on the received request") })
	public ResponseEntity<Flight> create(@PathVariable Integer flightId, @PathVariable Integer newairplaneId) {

		Airplane newAirplane = airplaneService.getairplane(newairplaneId);
		Flight savedFlight = flightService.updateflightairplane(flightService.getflight(flightId),
				newAirplane);
		return ResponseEntity.created(URI.create("/schedules/" + savedFlight.getId())).body(savedFlight);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a flight", notes = "Get the details for a flight based on the information from the database and the flight's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The flight was found"),
			@ApiResponse(code = 404, message = "The flight was not found") })
	public ResponseEntity<Flight> getflight(@PathVariable Integer id) {
		return ResponseEntity.ok().body(flightService.getflight(id));
	}

	@DeleteMapping(path = "/{flightId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Delete a flight", notes = "Delete a flight by destination id from the database and it's dependencies")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The flight was found"),
			@ApiResponse(code = 404, message = "The flight was not found") })
	public ResponseEntity<String> delete(@PathVariable Integer flightId) {
		List<Booking> bookings = bookingService.getAllbookingsByflight(flightId);
		for(Booking booking : bookings) {
			bookingService.deletebooking(booking.getId());
		}
		flightService.deleteflight(flightId);
		return ResponseEntity.ok().body("Succesfully deleted");
	}

}
