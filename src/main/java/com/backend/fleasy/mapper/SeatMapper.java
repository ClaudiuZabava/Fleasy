package com.backend.fleasy.mapper;

import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.SeatRequest;
import com.backend.fleasy.model.Seat;

@Component
public class SeatMapper {

	public Seat seatRequestToSeat(SeatRequest seatRequest) {

		return new Seat(seatRequest.getNumber());
	}
}
