package com.backend.fleasy.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.ScheduleRequest;
import com.backend.fleasy.mapper.ScheduleMapper;
import com.backend.fleasy.model.Flight;
import com.backend.fleasy.model.Schedule;
import com.backend.fleasy.service.FlightService;
import com.backend.fleasy.service.ScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/schedules")
@Api(value = "/schedules", tags = "Schedules")
public class ScheduleController {
	private ScheduleService scheduleService;
	private FlightService flightService;

	private ScheduleMapper scheduleMapper;

	public ScheduleController(ScheduleService scheduleService, FlightService flightService,
			ScheduleMapper scheduleMapper) {
		this.scheduleService = scheduleService;
		this.flightService = flightService;
		this.scheduleMapper = scheduleMapper;
	}

	@PutMapping(path = "/{flightId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update a schedule", notes = "Update a schedule based on a flight id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The schedule was successfully updated based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer flightId,
			@Valid @RequestBody @ApiParam(name = "schedule", value = "Schedule details", required = true) ScheduleRequest scheduleRequest) {
		Flight flight = flightService.getflight(flightId);
		Schedule savedSchedule = scheduleService.updateSchedule(flight.getSchedule(),
				scheduleMapper.scheduleRequestToSchedule(scheduleRequest));
		return ResponseEntity.ok().body(savedSchedule);
	}

}
