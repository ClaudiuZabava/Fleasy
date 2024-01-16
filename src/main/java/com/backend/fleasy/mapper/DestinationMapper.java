package com.backend.fleasy.mapper;

import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.DestinationRequest;
import com.backend.fleasy.model.Destination;

@Component
public class DestinationMapper {

	public Destination destinationRequestTodestination(DestinationRequest destinationRequest) {

		return new Destination(destinationRequest.getName(), destinationRequest.getType());
	}
}
