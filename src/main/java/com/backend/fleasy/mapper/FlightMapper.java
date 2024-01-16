package com.backend.fleasy.mapper;

import com.backend.fleasy.model.Flight;
import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.FlightRequest;

@Component
public class FlightMapper {

	private ScheduleMapper scheduleMapper;

	public FlightMapper(ScheduleMapper scheduleMapper) {
		this.scheduleMapper = scheduleMapper;
	}

	public Flight flightRequestToflight(FlightRequest flightRequest) {

		return new Flight(scheduleMapper.scheduleRequestToSchedule(flightRequest.getSchedule()));
	}
}
