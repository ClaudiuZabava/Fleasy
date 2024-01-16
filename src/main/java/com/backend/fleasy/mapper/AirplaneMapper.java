package com.backend.fleasy.mapper;

import com.backend.fleasy.model.Airplane;
import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.AirplaneRequest;

@Component
public class AirplaneMapper {

	public Airplane airplaneRequestToairplane(AirplaneRequest airplaneRequest) {

		return new Airplane(airplaneRequest.getName(), airplaneRequest.getCapacity());
	}
}
