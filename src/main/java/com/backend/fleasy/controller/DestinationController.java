package com.backend.fleasy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fleasy.dto.DestinationRequest;
import com.backend.fleasy.mapper.DestinationMapper;
import com.backend.fleasy.model.Destination;
import com.backend.fleasy.service.DestinationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/destinations")
@Api(value = "/destinations", tags = "Destinations")
public class DestinationController {

	private DestinationService destinationService;
	private DestinationMapper destinationMapper;

	public DestinationController(DestinationService destinationService, DestinationMapper destinationMapper) {
		this.destinationService = destinationService;
		this.destinationMapper = destinationMapper;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a destination", notes = "Creates a new destination based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Destination was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Destination> create(
			@Valid @RequestBody @ApiParam(name = "destination", value = "Destination details", required = true) DestinationRequest destinationRequest) {
		Destination savedDestination = destinationService.createdestination(destinationMapper.destinationRequestTodestination(destinationRequest));
		return ResponseEntity.created(URI.create("/destinations/" + savedDestination.getId())).body(savedDestination);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create destinations", notes = "Creates new destinations based on list of destinations received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The destinations were successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<List<Destination>> createBulk(
			@Valid @RequestBody @ApiParam(name = "destinations", value = "List with destinations details", required = true) List<DestinationRequest> listDestinationRequest) {
		for (DestinationRequest destinationRequest : listDestinationRequest) {
			Destination savedDestination = destinationService.createdestination(destinationMapper.destinationRequestTodestination(destinationRequest));
		}
		return ResponseEntity.ok().body(destinationService.getAlldestinations());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a destination", notes = "Get the details for a destination based on the information from the database and the destination's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The destination was found"),
			@ApiResponse(code = 404, message = "The destination was not found") })
	public ResponseEntity<Destination> getdestination(@PathVariable Integer id) {
		return ResponseEntity.ok().body(destinationService.getdestination(id));
	}

}
