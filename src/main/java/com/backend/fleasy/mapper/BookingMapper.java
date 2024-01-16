package com.backend.fleasy.mapper;

import org.springframework.stereotype.Component;

import com.backend.fleasy.dto.BookingRequest;
import com.backend.fleasy.model.Booking;

@Component
public class BookingMapper {

	public Booking bookingRequestTobooking(BookingRequest bookingRequest) {

		return new Booking(bookingRequest.getNoPersons(), bookingRequest.getDateRegistered());
	}
}
