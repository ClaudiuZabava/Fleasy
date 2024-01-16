package com.backend.fleasy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.backend.fleasy.model.Airplane;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.AirplaneRequest;
import com.backend.fleasy.mapper.AirplaneMapper;
import com.backend.fleasy.model.Seat;
import com.backend.fleasy.service.AirplaneService;
import com.backend.fleasy.service.SeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/planes")
@Api(value = "/planes", tags = "Planes")
public class AirplaneController {
	private AirplaneService airplaneService;
	private AirplaneMapper airplaneMapper;
	private SeatService seatService;

	public AirplaneController(AirplaneService airplaneService, AirplaneMapper airplaneMapper, SeatService seatService) {
		this.airplaneService = airplaneService;
		this.airplaneMapper = airplaneMapper;
		this.seatService = seatService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a airplane", notes = "Creates a new plane based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Airplane was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Airplane> create(
			@Valid @RequestBody @ApiParam(name = "airplane", value = "Airplane details", required = true) AirplaneRequest airplaneRequest) {

		Airplane savedAirplane = airplaneService.createairplane(airplaneMapper.airplaneRequestToairplane(airplaneRequest)); // create plane
		List<Seat> seats = seatService.createSeats(savedAirplane); // create seats
		savedAirplane = airplaneService.saveSeats(seats, savedAirplane); // save seats for plane
		return ResponseEntity.created(URI.create("/planes/" + savedAirplane.getId())).body(savedAirplane);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create airplanes", notes = "Creates new planes based on list of airplanes received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Airplanes were successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<List<Airplane>> createBulk(
			@Valid @RequestBody @ApiParam(name = "airplanes", value = "List with Airplanes details", required = true) List<AirplaneRequest> listAirplaneRequest) {
		for (AirplaneRequest airplaneRequest : listAirplaneRequest) {
			Airplane savedAirplane = airplaneService.createairplane(airplaneMapper.airplaneRequestToairplane(airplaneRequest)); // create airplane
			List<Seat> seats = seatService.createSeats(savedAirplane); // create seats
			savedAirplane = airplaneService.saveSeats(seats, savedAirplane);
		}
		return ResponseEntity.ok().body(airplaneService.getAllairplanes());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a airplane", notes = "Get the details for a airplane based on the information from the database and the airplane's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The airplane was found"),
			@ApiResponse(code = 404, message = "The airplane was not found") })
	public ResponseEntity<Airplane> getairplane(@PathVariable Integer id) {
		return ResponseEntity.ok().body(airplaneService.getairplane(id));
	}
}
