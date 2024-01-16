package com.backend.fleasy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.backend.fleasy.model.Booking;
import com.backend.fleasy.service.PassengerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.BookingRequest;
import com.backend.fleasy.mapper.BookingMapper;
import com.backend.fleasy.model.Seat;
import com.backend.fleasy.service.FlightService;
import com.backend.fleasy.service.BookingService;
import com.backend.fleasy.service.SeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/bookings")
@Api(value = "/bookings", tags = "Bookings")
public class BookingController {

	private BookingService bookingService;
	private PassengerService passengerService;
	private FlightService flightService;
	private SeatService seatService;

	private BookingMapper bookingMapper;

	public BookingController(BookingService bookingService, PassengerService passengerService,
							 FlightService flightService, SeatService seatService, BookingMapper bookingMapper) {
		this.bookingService = bookingService;
		this.passengerService = passengerService;
		this.flightService = flightService;
		this.seatService = seatService;
		this.bookingMapper = bookingMapper;
	}

	@PostMapping(path = "/{userId}/{flightId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a booking", notes = "Creates a new booking based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Booking was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Booking> create(@PathVariable Integer userId, @PathVariable Integer flightId,
										  @Valid @RequestBody @ApiParam(name = "booking", value = "Booking details", required = true) BookingRequest bookingRequest) {
		List<Seat> seats = seatService.getSeatsFromList(bookingRequest.getSeatIds());
		Booking savedBooking = bookingService.createbooking(
				bookingMapper.bookingRequestTobooking(bookingRequest), passengerService.getpassenger(userId),
				flightService.getflight(flightId), seats);
		return ResponseEntity.created(URI.create("/bookings/" + savedBooking.getId())).body(savedBooking);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a booking", notes = "Get the details for a booking based on the information from the database and the booking's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The booking was found"),
			@ApiResponse(code = 404, message = "The booking was not found") })
	public ResponseEntity<Booking> getbooking(@PathVariable Integer id) {
		return ResponseEntity.ok().body(bookingService.getbooking(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Cancel a booking", notes = "Cancel a booking by id from the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The booking was found"),
			@ApiResponse(code = 404, message = "The booking was not found") })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		bookingService.deletebooking(id);

		return ResponseEntity.ok().body("Succesfully canceled");
	}

}
