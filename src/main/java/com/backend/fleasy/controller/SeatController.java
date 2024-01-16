package com.backend.fleasy.controller;

import java.util.Dictionary;
import java.util.List;

import com.backend.fleasy.model.Airplane;
import com.backend.fleasy.model.Flight;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.model.Booking;
import com.backend.fleasy.model.Seat;
import com.backend.fleasy.service.FlightService;
import com.backend.fleasy.service.BookingService;
import com.backend.fleasy.service.SeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/seats")
@Api(value = "/seats", tags = "Seats")
public class SeatController {

	private SeatService seatService;
	private BookingService bookingService;
	private FlightService flightService;

	public SeatController(SeatService seatService, BookingService bookingService,
			FlightService flightService) {
		this.seatService = seatService;
		this.bookingService = bookingService;
		this.flightService = flightService;
	}

	@GetMapping(path = "/{flightId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get the free seats", notes = "Get the list of free seats for a certain flight based on the flight id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The seat was found"),
			@ApiResponse(code = 404, message = "The seat was not found") })
	public ResponseEntity<Dictionary<Airplane, List<Seat>>> getFreeSeats(@PathVariable Integer flightId) {
		Flight flight = flightService.getflight(flightId);
		List<Booking> bookings = bookingService.getAllbookingsByflight(flightId);
		return ResponseEntity.ok().body(seatService.getFreeSeats(bookings, flight));
	}

}
